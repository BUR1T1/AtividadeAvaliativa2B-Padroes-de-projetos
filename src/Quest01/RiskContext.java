package Quest01;

import java.util.HashMap;
import java.util.Map;

public class RiskContext {
   
    private final Map<String, Double> financialData;
    private final String scenario;
    private final double confidenceLevel;
    private final int horizonDays;

    // Construtor privado: só o Builder pode chamar
    private RiskContext(Builder builder) {
        this.financialData = builder.financialData;
        this.scenario = builder.scenario;
        this.confidenceLevel = builder.confidenceLevel;
        this.horizonDays = builder.horizonDays;
    }

    public Map<String, Double> getFinancialData() {
        return financialData;
    }

    public String getScenario() {
        return scenario;
    }

    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    public int getHorizonDays() {
        return horizonDays;
    }

    public static class Builder {
        private Map<String, Double> financialData = new HashMap<>();
        private String scenario = "DEFAULT";
        private double confidenceLevel = 0.95;
        private int horizonDays = 10;

        public Builder withFinancialData(Map<String, Double> financialData) {
            this.financialData = financialData;
            return this;
        }

        public Builder withScenario(String scenario) {
            this.scenario = scenario;
            return this;
        }

        public Builder withConfidenceLevel(double confidenceLevel) {
            this.confidenceLevel = confidenceLevel;
            return this;
        }

        public Builder withHorizonDays(int horizonDays) {
            this.horizonDays = horizonDays;
            return this;
        }

        public RiskContext build() {
            return new RiskContext(this);
        }
    }
}
