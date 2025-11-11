package Quest01;

public class RiskProcessor {
        private RiskAlgorithm algorithm;

        public RiskProcessor(RiskAlgorithm algorithm) {
            this.algorithm = algorithm;
        }

        // Permite trocar o algoritmo a qualquer momento
        public void setAlgorithm(RiskAlgorithm algorithm) {
            this.algorithm = algorithm;
        }

        // Executa o cálculo de risco usando o algoritmo atual
        public String process(RiskContext context) {
            return algorithm.calculateRisk(context);
        }
    }
