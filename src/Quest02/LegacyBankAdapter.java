package Quest02;

import java.util.HashMap;
import java.util.Map;

public class LegacyBankAdapter implements TransactionProcessor{
    private final LegacyBankSystem legacySystem;

    public LegacyBankAdapter(LegacyBankSystem legacySystem) {
        this.legacySystem = legacySystem;
    }

    // Mapa de códigos de moeda exigido pelo sistema legado
    private static final Map<String, Integer> currencyCodes = Map.of(
            "USD", 1,
            "EUR", 2,
            "BRL", 3
    );

    @Override
    public String authorize(String cardNumber, double value, String currency) {
        // Cria parâmetros no formato esperado pelo legado
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("numeroCartao", cardNumber);
        parametros.put("valorTransacao", value);
        parametros.put("codigoMoeda", currencyCodes.getOrDefault(currency.toUpperCase(), 0));

        // Campo obrigatório no legado que não existe na interface moderna
        parametros.put("codigoInstituicao", "999"); // Exemplo fixo

        // Envia pro sistema legado
        HashMap<String, Object> resposta = legacySystem.processTransaction(parametros);

        // Converte a resposta legado para formato moderno (String)
        return convertLegacyResponse(resposta);
    }

    // Método auxiliar: converte resposta do legado para formato moderno
    private String convertLegacyResponse(HashMap<String, Object> resposta) {
        int status = (int) resposta.get("statusCode");
        String msg = (String) resposta.get("mensagem");
        long id = (long) resposta.get("idTransacao");

        return String.format("Status: %d | Mensagem: %s | ID: %d", status, msg, id);
    }
}
