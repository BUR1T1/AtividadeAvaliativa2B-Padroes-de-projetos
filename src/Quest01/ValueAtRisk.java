package Quest01;

public class ValueAtRisk implements RiskAlgorithm{

    @Override
    public String calculateRisk(RiskContext context) {
        return String.format("Calculando Value at Risk (dummy) - Confiança: %.2f | Cenário: %s",
                context.getConfidenceLevel(), context.getScenario());
    }
}
