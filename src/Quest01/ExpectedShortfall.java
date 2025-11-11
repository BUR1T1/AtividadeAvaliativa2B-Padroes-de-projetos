package Quest01;

public class ExpectedShortfall implements RiskAlgorithm {
    @Override
    public String calculateRisk(RiskContext context) {
        return String.format("Calculando Expected Shortfall (dummy) - Cenário: %s | Dados: %d entradas",
                context.getScenario(), context.getFinancialData().size());
    }
}