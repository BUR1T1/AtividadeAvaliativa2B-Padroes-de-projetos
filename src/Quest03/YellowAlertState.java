package Quest03;

public class YellowAlertState implements ReactorState {
    @Override
    public void handle(ReactorContext context) {
        if (context.isMaintenanceMode()) {
            context.setState(new MaintenanceState());
            return;
        }
        if (context.getTemperature() > 400) {
            long now = System.currentTimeMillis();
            if (context.getAlertYellowStartTime() == 0)
                context.setAlertYellowStartTime(now);
            else if (now - context.getAlertYellowStartTime() > 30000)
                context.setState(new RedAlertState());
            else
                System.out.println("Alerta amarelo — monitorando temperatura...");
        } else {
            context.setState(new NormalOperationState());
        }
    }
    public String getName() { return "ALERTA_AMARELO"; }
}
