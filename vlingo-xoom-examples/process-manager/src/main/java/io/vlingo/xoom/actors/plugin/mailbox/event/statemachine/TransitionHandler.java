package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.common.Completes;

public class TransitionHandler<T extends State, R extends State> {

    private String path;
    private Completes<StateTransition<T, R>> stateTransition;

    public TransitionHandler() {
    }

    public TransitionHandler<T, R> withPath(String name) {
        this.path = name;
        return this;
    }


    public TransitionHandler<T, R> withTransition(StateHandler<T, R> handler) {
        this.stateTransition = handler.execute();
        return this;
    }


    public String getPath() {
        return path;
    }

    public Completes<StateTransition<T, R>> getStateTransition() {
        return stateTransition;
    }

    public void setStateTransition(Completes<StateTransition<T, R>> stateTransition) {
        this.stateTransition = stateTransition;
    }

    public static <T1 extends State, R1 extends State> TransitionHandler<T1, R1> handleFor(
            Class<T1> t1,
            Class<R1> r1) {
        return new TransitionHandler<>();
    }
}
