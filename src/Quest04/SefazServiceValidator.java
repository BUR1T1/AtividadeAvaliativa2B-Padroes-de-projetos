package Quest04;

public class SefazServiceValidator implements Validator {
    public String name() { return "SefazServiceValidator"; }
    public int timeoutSeconds() { return 5; }

    public ValidationResult validate(FiscalDocument doc) throws Exception {
        // Simula consulta remota (timeout controlado externamente)
        Thread.sleep(600);
        // Simulação: se metadata contains "sefazDown" true -> falha
        Object down = doc.getMetadata().get("sefazDown");
        if (down instanceof Boolean && (Boolean) down) {
            return ValidationResult.fail("SEFAZ indisponível", true); // falha, mas não necessariamente grave
        }
        return ValidationResult.ok("SEFAZ consultado OK");
    }
}

