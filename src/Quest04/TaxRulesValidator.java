package Quest04;

public class TaxRulesValidator implements Validator {
    public String name() { return "TaxRulesValidator"; }
    public int timeoutSeconds() { return 4; }

    public ValidationResult validate(FiscalDocument doc) throws Exception {
        Thread.sleep(500);
        // Simulação: se metadata contém "invalidTax" -> falha
        Object invalidTax = doc.getMetadata().get("invalidTax");
        if (invalidTax instanceof Boolean && (Boolean) invalidTax) {
            return ValidationResult.fail("Regras fiscais inválidas", false); // falha grave
        }
        return ValidationResult.ok("Regras fiscais OK");
    }
}
