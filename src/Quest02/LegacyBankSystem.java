package Quest02;

import java.util.HashMap;

public class LegacyBankSystem {
    public HashMap<String, Object> processTransaction(HashMap<String, Object> parameters) {
        // Simulação do retorno legado
        HashMap<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("mensagem", "Transação aprovada com sucesso (legado)");
        response.put("idTransacao", Math.round(Math.random() * 10000));
        return response;
    }
}
