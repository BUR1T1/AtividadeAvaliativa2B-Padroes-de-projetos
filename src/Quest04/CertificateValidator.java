package Quest04;

public class CertificateValidator implements Validator {
    public String name() { return "CertificateValidator"; }
    public int timeoutSeconds() { return 2; }

    public ValidationResult validate(FiscalDocument doc) throws Exception {
        Thread.sleep(300);
        // Simulação: se metadata contains "certExpired" true -> falha
        Object expired = doc.getMetadata().get("certExpired");
        if (expired instanceof Boolean && (Boolean) expired) {
            // Se o certificado expirou, falha mas permitimos continuar (ex: não é fatal aqui)
            return ValidationResult.fail("Certificado expirado", true);
        }
        return ValidationResult.ok("Certificado OK");
    }
}
