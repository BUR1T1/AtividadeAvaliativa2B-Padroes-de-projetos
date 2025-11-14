package Quest03;

public class MaintenanceState implements ReactorState {
    @Override
    public void handle(ReactorContext context) {
        System.out.println(" Modo manutenção ativo — ignorando regras normais.");
    }
    public String getName() { return "MANUTENCAO"; }
}
