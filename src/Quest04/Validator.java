package Quest04;

public interface Validator {
    String name();
    ValidationResult validate(FiscalDocument doc) throws Exception;
    int timeoutSeconds();
}

