package Quest04;

import java.util.HashSet;
import java.util.Set;

public class DatabaseValidator implements Validator {
    private final Set<String> fakeDb = new HashSet<>(); // simula DB de números NF-e

    public String name() { return "DatabaseValidator"; }
    public int timeoutSeconds() { return 3; }

    public ValidationResult validate(FiscalDocument doc) throws Exception {
        Thread.sleep(400);
        String num = (String) doc.getMetadata().getOrDefault("numero", "NUM-DEFAULT");
        if (fakeDb.contains(num)) {
            return ValidationResult.fail("Duplicidade detectada no DB", false);
        }
        // Simula inserção no DB e retorna rollbackAction para apagar se necessário
        fakeDb.add(num);
        Runnable rollback = () -> {
            fakeDb.remove(num);
            System.out.println("[DB] rollback: removido numero " + num);
        };
        // Marca no documento que inserimos para possível uso
        doc.getMetadata().put("dbInserted", true);
        return ValidationResult.okWithRollback("Inserido no DB: " + num, rollback);
    }
}

