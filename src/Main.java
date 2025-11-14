import Quest01.*;

import Quest04.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {


       /* // Criando contexto financeiro via Builder (imutável e legível)
        RiskContext context = new RiskContext.Builder()
                .withScenario("CRASH_2025")
                .withConfidenceLevel(0.99)
                .withHorizonDays(15)
                .build();

        // Criando algoritmo inicial via Factory
        RiskAlgorithm algorithm = RiskAlgorithmFactory.create("VAR");

        // Injetando o algoritmo no Processor
        RiskProcessor processor = new RiskProcessor(algorithm);

        System.out.println("=== Value at Risk ===");
        System.out.println(processor.process(context));

        // Trocando algoritmo em tempo de execução
        System.out.println("\n=== Expected Shortfall ===");
        processor.setAlgorithm(RiskAlgorithmFactory.create("ES"));
        System.out.println(processor.process(context));

        // Trocando novamente
        System.out.println("\n=== Stress Testing ===");
        processor.setAlgorithm(RiskAlgorithmFactory.create("STRESS"));
        System.out.println(processor.process(context));*/

        /*O padrão usado foi o Strategy, porque ele deixa o código mais organizado e fácil de mudar sem precisar encher de if ou switch.
No meu código, isso aparece na interface RiskAlgorithm e nas classes que a implementam, como ValueAtRisk, ExpectedShortfall e StressTesting.
Cada uma dessas classes representa um tipo diferente de cálculo de risco, e todas seguem o mesmo padrão de método calculateRisk().

Com isso, quando o sistema precisa trocar o tipo de cálculo, eu só troco a estratégia usada — por exemplo, no RiskProcessor, eu posso mudar de ValueAtRisk para ExpectedShortfall sem alterar o restante do código.
Esse padrão também facilita a manutenção, porque cada algoritmo tem sua própria classe e responsabilidade bem definida.

Em resumo, o Strategy foi usado pra permitir que os algoritmos de risco fossem trocados dinamicamente, deixando o código mais limpo, flexível e sem aquele excesso de condicionais.*/



        // --------------------- Quest02

       /* // Instancia o sistema legado e o adaptador
        LegacyBankSystem legacySystem = new LegacyBankSystem();
        TransactionProcessor processor = new LegacyBankAdapter(legacySystem);

        // Teste com diferentes moedas
        System.out.println("=== Teste com cartão BRL ===");
        System.out.println(processor.authorize("5555 8888 7777 2222", 250.00, "BRL"));

        System.out.println("\n=== Teste com cartão USD ===");
        System.out.println(processor.authorize("4111 1111 1111 1111", 100.00, "USD"));

        System.out.println("\n=== Teste com moeda inválida ===");
        System.out.println(processor.authorize("9999 4444 3333 2222", 50.00, "JPY"));*/

       /* ReactorContext reactor = new ReactorContext();

        reactor.setState(new NormalOperationState());
        reactor.setTemperature(310);
        reactor.handle();

        Thread.sleep(2000);
        reactor.setTemperature(410);
        reactor.handle();

        Thread.sleep(31000);
        reactor.handle();

        reactor.setCoolingFailure(true);
        reactor.handle();

        reactor.enableMaintenanceMode();
        reactor.handle();*/

        // monta validadores na ordem requerida
        List<Validator> validators = List.of(
                new XmlSchemaValidator(),     // 1
                new CertificateValidator(),   // 2
                new TaxRulesValidator(),      // 3 (só se anteriores passarem)
                new DatabaseValidator(),      // 4 (faz insert -> precisa de rollback)
                new SefazServiceValidator()   // 5 (só se anteriores passarem)
        );

        ValidationChainRunner runner = new ValidationChainRunner(validators, 3);

        // caso 1: documento OK
        FiscalDocument docOk = new FiscalDocument("NF1", "<xml><ok/></xml>");
        docOk.getMetadata().put("numero", "0001");
        ValidationReport r1 = runner.run(docOk);
        System.out.println("Caso OK: " + r1);

        // caso 2: schema inválido -> interrompe imediatamente (fail grave)
        FiscalDocument docBadSchema = new FiscalDocument("NF2", "<invalid></invalid>");
        docBadSchema.getMetadata().put("numero", "0002");
        ValidationReport r2 = runner.run(docBadSchema);
        System.out.println("Caso schema inválido: " + r2);

        // caso 3: certificado expirado (falha não fatal) -> depois taxRules e sefaz são pulados conforme regras
        FiscalDocument docCertExpired = new FiscalDocument("NF3", "<xml><ok/></xml>");
        docCertExpired.getMetadata().put("certExpired", true);
        docCertExpired.getMetadata().put("numero", "0003");
        ValidationReport r3 = runner.run(docCertExpired);
        System.out.println("Caso cert expirado: " + r3);

        // caso 4: duplicidade no DB e depois falha subsequente para forçar rollback
        FiscalDocument docDupThenFail = new FiscalDocument("NF4", "<xml><ok/></xml>");
        docDupThenFail.getMetadata().put("numero", "0001"); // usa numero já inserido no caso OK -> duplicidade
        // também força sefaz down para aumentar falhas
        docDupThenFail.getMetadata().put("sefazDown", true);
        ValidationReport r4 = runner.run(docDupThenFail);
        System.out.println("Caso duplicidade + sefaz down: " + r4);

        // caso 5: validador lento (simula timeout)
        Validator slowValidator = new Validator() {
            public String name() { return "SlowValidator"; }
            public int timeoutSeconds() { return 1; } 
            public ValidationResult validate(FiscalDocument doc) throws Exception {
                Thread.sleep(3000); 
                return ValidationResult.ok("Slow ok");
            }
        };

        List<Validator> validatorsWithSlow = new ArrayList<>(validators);
        validatorsWithSlow.add(2, slowValidator);
        ValidationChainRunner runner2 = new ValidationChainRunner(validatorsWithSlow, 3);

        FiscalDocument docSlow = new FiscalDocument("NF5", "<xml><ok/></xml>");
        docSlow.getMetadata().put("numero", "0005");
        ValidationReport r5 = runner2.run(docSlow);
        System.out.println("Caso timeout (slow validator): " + r5);

        runner.shutdown();
        runner2.shutdown();
    }
}



