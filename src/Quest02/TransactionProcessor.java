package Quest02;

public interface TransactionProcessor {
    String authorize(String cardNumber, double value, String currency);
}
