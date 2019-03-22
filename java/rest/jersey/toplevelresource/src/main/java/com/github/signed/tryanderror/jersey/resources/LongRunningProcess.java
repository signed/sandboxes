package com.github.signed.tryanderror.jersey.resources;

public class LongRunningProcess {

    public static enum State{
        Created,
        Running,
        Completed
    }

    public final ProcessIdentifier identifier;
    private final ProcessInput input;
    private State state ;

    public static LongRunningProcess createNewLongRunningProcess(ProcessIdentifier identifier, ProcessInput input) {
        return new LongRunningProcess(identifier, input,  State.Created);
    }

    public LongRunningProcess(ProcessIdentifier identifier, ProcessInput input, State state) {
        this.identifier = identifier;
        this.input = input;
        this.state = state;
    }

    public void start() {
        this.state = State.Running;
    }

    public void completed() {
        this.state = State.Completed;
    }

    @Override
    public String toString() {

        return String.format("[%s][%s]  %s", identifier.toString(), state.toString(), input.toString());
    }
}