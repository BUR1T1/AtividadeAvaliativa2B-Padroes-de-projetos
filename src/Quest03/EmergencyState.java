package Quest03;

public class EmergencyState implements ReactorState {
    @Override
    public void handle(ReactorContext context) {
        if (context.isMaintenanceMode()) {
            context.setState(new MaintenanceState());
            return;
        }
        System.out.println("EMERGÊNCIA! Desligamento total!");
    }
    public String getName() { return "EMERGENCIA"; }
}
