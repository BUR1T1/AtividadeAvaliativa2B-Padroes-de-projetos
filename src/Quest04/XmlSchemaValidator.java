package Quest04;

public class XmlSchemaValidator implements Validator {
    public String name() {
        return "XmlSchemaValidator";
    }
    public int timeoutSeconds() {
        return 3;
    }

    public ValidationResult validate(FiscalDocument doc) throws Exception {
        // Simulamos validação: falha se xml contiver "<invalid>"
        Thread.sleep(200); // simula trabalho
        if (doc.getXml() == null || doc.getXml().contains("<invalid>")) {
            return ValidationResult.fail("Schema inválido", false); // falha grave: interrompe a cadeia
        }
        return ValidationResult.ok("Schema OK");
    }
}
