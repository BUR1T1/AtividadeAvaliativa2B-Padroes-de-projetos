package Quest03;

public class ReactorContext {

    private ReactorState currentState;
    private double temperature;
    private boolean coolingFailure;
    private boolean maintenanceMode;
    private long alertYellowStartTime;

    public ReactorContext() {
        this.currentState = new OffState();
    }

    public void setState(ReactorState newState) {
        System.out.println("Transição: " + currentState.getName() + " → " + newState.getName());
        this.currentState = newState;
    }

    public void setTemperature(double temperature) { this.temperature = temperature; }
    public double getTemperature() { return temperature; }
    public void setCoolingFailure(boolean failure) { this.coolingFailure = failure; }
    public boolean isCoolingFailure() { return coolingFailure; }

    public void enableMaintenanceMode() {
        this.maintenanceMode = true;
        System.out.println(" Modo manutenção ativado");
    }

    public boolean isMaintenanceMode() { return maintenanceMode; }

    public void setAlertYellowStartTime(long time) { this.alertYellowStartTime = time; }
    public long getAlertYellowStartTime() { return alertYellowStartTime; }

    public void handle() { currentState.handle(this); }
}
