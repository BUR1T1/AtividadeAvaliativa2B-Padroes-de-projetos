import Quest01.*;
import Quest02.LegacyBankAdapter;
import Quest02.LegacyBankSystem;
import Quest02.TransactionProcessor;
import Quest03.NormalOperationState;
import Quest03.ReactorContext;

import java.util.HashMap;
import java.util.Map;

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

        ReactorContext reactor = new ReactorContext();

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
        reactor.handle();
    }
}



