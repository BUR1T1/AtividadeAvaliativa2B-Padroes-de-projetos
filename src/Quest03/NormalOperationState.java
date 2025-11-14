package Quest03;

public class NormalOperationState implements ReactorState {
    @Override
    public void handle(ReactorContext context) {
        if (context.isMaintenanceMode()) {
            context.setState(new MaintenanceState());
            return;
        }
        if (context.getTemperature() > 300)
            context.setState(new YellowAlertState());
        else
            System.out.println(" Operação normal.");
    }
    public String getName() { return "OPERACAO_NORMAL"; }
}
