package Quest03;

public class OffState implements ReactorState {
    @Override
    public void handle(ReactorContext context) {
        if (context.isMaintenanceMode())
            context.setState(new MaintenanceState());
        else
            System.out.println("Reator desligado.");
    }
    public String getName() { return "DESLIGADA"; }
}
