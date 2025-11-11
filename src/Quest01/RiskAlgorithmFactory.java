package Quest01;

public class RiskAlgorithmFactory {
    public static RiskAlgorithm create(String type) {
        switch (type.toUpperCase()) {
            case "VAR": return new ValueAtRisk();
            case "ES": return new ExpectedShortfall();
            case "STRESS": return new StressTesting();
            default: throw new IllegalArgumentException("Tipo de algoritmo desconhecido: " + type);
        }
    }
}
