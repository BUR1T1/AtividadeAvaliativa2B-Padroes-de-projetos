package Quest03;

public class RedAlertState implements ReactorState {
    @Override
    public void handle(ReactorContext context) {
        if (context.isMaintenanceMode()) {
            context.setState(new MaintenanceState());
            return;
        }
        if (context.isCoolingFailure())
            context.setState(new EmergencyState());
        else if (context.getTemperature() < 300)
            context.setState(new NormalOperationState());
        else
            System.out.println("Alerta vermelho! Risco alto!");
    }
    public String getName() { return "ALERTA_VERMELHO"; }
}

