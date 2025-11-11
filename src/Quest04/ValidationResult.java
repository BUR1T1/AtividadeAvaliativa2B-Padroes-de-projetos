package Quest04;

import java.util.Collections;
import java.util.Set;

public class ValidationResult {
    public final boolean success;
    public final String message;
    public final boolean continueChain; // se false, interrompe a cadeia (p.ex. falha grave)
    public final Set<String> skipValidators; // nomes de validadores para pular
    public final Runnable rollbackAction; // ação de rollback se gerou modificação (pode ser null)

    private ValidationResult(boolean success, String message, boolean continueChain, Set<String> skipValidators, Runnable rollbackAction) {
        this.success = success;
        this.message = message;
        this.continueChain = continueChain;
        this.skipValidators = skipValidators == null ? Collections.emptySet() : Collections.unmodifiableSet(skipValidators);
        this.rollbackAction = rollbackAction;
    }

    public static ValidationResult ok(String message) {
        return new ValidationResult(true, message, true, null, null);
    }

    public static ValidationResult okWithRollback(String message, Runnable rollbackAction) {
        return new ValidationResult(true, message, true, null, rollbackAction);
    }

    public static ValidationResult fail(String message, boolean continueChain) {
        return new ValidationResult(false, message, continueChain, null, null);
    }

    public static ValidationResult failAndSkip(String message, Set<String> skipValidators) {
        return new ValidationResult(false, message, true, skipValidators, null);
    }
}

