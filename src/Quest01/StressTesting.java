package Quest01;

public class StressTesting implements RiskAlgorithm{

    @Override
    public String calculateRisk(RiskContext context) {
        return String.format("Executando Stress Testing (dummy) - Cenário: %s | Horizonte: %d dias",
                context.getScenario(), context.getHorizonDays());
    }
}
