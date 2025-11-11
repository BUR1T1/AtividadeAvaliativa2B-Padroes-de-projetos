package Quest04;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ValidationChainRunner {
    private final List<Validator> validators;
    private final ExecutorService executor;
    private final int circuitBreakerThreshold;

    public ValidationChainRunner(List<Validator> validators, int circuitBreakerThreshold) {
        this.validators = new ArrayList<>(validators);
        this.executor = Executors.newCachedThreadPool();
        this.circuitBreakerThreshold = circuitBreakerThreshold;
    }

    public ValidationReport run(FiscalDocument doc) {
        int failureCount = 0;
        Set<String> skipSet = new HashSet<>();
        Deque<Runnable> rollbackActions = new ArrayDeque<>();
        List<String> logs = new ArrayList<>();

        for (Validator v : validators) {
            if (skipSet.contains(v.name())) {
                logs.add("[SKIP] " + v.name());
                continue;
            }

            // regras específicas: TaxRulesValidator e SefazServiceValidator só executam se anteriores passaram
            if ((v instanceof TaxRulesValidator || v instanceof SefazServiceValidator) && failureCount > 0) {
                logs.add("[COND-SKIP] " + v.name() + " (falha anterior)");
                continue;
            }

            // Circuit breaker: interrompe após threshold falhas
            if (failureCount >= circuitBreakerThreshold) {
                logs.add("[CIRCUIT-BREAKER] interrompendo após " + failureCount + " falhas.");
                break;
            }

            // Executa com timeout
            Future<ValidationResult> future = executor.submit(() -> v.validate(doc));
            ValidationResult result;
            try {
                result = future.get(v.timeoutSeconds(), TimeUnit.SECONDS);
            } catch (TimeoutException te) {
                future.cancel(true);
                failureCount++;
                logs.add("[TIMEOUT] " + v.name() + " excedeu " + v.timeoutSeconds() + "s");
                // Se algum validador que inseriu DB já fez modificação, faremos rollback depois (deferred)
                if (failureCount >= circuitBreakerThreshold) {
                    logs.add("[CIRCUIT-BREAKER] acionado por timeout.");
                    break;
                }
                continue;
            } catch (Exception ex) {
                future.cancel(true);
                failureCount++;
                logs.add("[ERROR] " + v.name() + " -> " + ex.getMessage());
                if (failureCount >= circuitBreakerThreshold) {
                    logs.add("[CIRCUIT-BREAKER] acionado por erro.");
                    break;
                }
                continue;
            }

            // Processa resultado
            logs.add("[" + (result.success ? "OK" : "FAIL") + "] " + v.name() + " : " + result.message);

            if (result.rollbackAction != null) {
                rollbackActions.push(result.rollbackAction);
            }

            if (!result.success) {
                failureCount++;
                // Se o validador pediu para pular determinados validadores
                if (!result.skipValidators.isEmpty()) {
                    skipSet.addAll(result.skipValidators);
                    logs.add("[SKIP-CMD] a cadeia irá pular: " + result.skipValidators);
                }
                if (!result.continueChain) {
                    logs.add("[INTERRUPT] cadeia interrompida por: " + v.name());
                    break;
                }
            }
        }

        boolean overallSuccess = failureCount == 0;
        // se houver falhas após um validador que inseriu no DB (validador 4), devemos rodar rollback
        if (!overallSuccess && !rollbackActions.isEmpty()) {
            logs.add("[ROLLBACK] iniciando rollback de ações realizadas...");
            while (!rollbackActions.isEmpty()) {
                try {
                    rollbackActions.pop().run();
                } catch (Exception ex) {
                    logs.add("[ROLLBACK-ERROR] " + ex.getMessage());
                }
            }
            logs.add("[ROLLBACK] concluído.");
        }

        return new ValidationReport(overallSuccess, failureCount, logs);
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}
