package Quest03;

interface ReactorState {
    void handle(ReactorContext context);
    String getName();
}
