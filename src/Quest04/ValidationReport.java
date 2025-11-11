package Quest04;

import java.util.List;

public class ValidationReport {
    public final boolean success;
    public final int failureCount;
    public final List<String> logs;

    public ValidationReport(boolean success, int failureCount, List<String> logs) {
        this.success = success;
        this.failureCount = failureCount;
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "ValidationReport{success=" + success + ", failures=" + failureCount + ", logs=" + logs + "}";
    }
}

