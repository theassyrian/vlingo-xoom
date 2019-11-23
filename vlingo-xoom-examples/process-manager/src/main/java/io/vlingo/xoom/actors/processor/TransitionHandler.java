package io.vlingo.xoom.actors.processor;

/**
 * A {@link TransitionHandler} subscribes to a {@link StateTransition} and is used to perform a transaction in response
 * to a {@link StateTransition}.
 *
 * @param <T> is the source state
 * @param <R> is the target state
 * @author Kenny Bastani
 */
public class TransitionHandler<T extends State, R extends State> {

    private String path;
    private StateTransition<T, R> stateTransition;

    public TransitionHandler() {
    }

    public TransitionHandler<T, R> withPath(String name) {
        this.path = name;
        return this;
    }


    public TransitionHandler<T, R> withTransition(StateHandler<T, R> handler) {
        this.stateTransition = handler.execute().outcome();
        return this;
    }


    public String getPath() {
        return path;
    }

    public StateTransition<T, R> getStateTransition() {
        return stateTransition;
    }

    public void setStateTransition(StateTransition<T, R> stateTransition) {
        this.stateTransition = stateTransition;
    }

    public static <T1 extends State, R1 extends State> TransitionHandler<T1, R1> handleFor(
            Class<T1> t1,
            Class<R1> r1) {
        return new TransitionHandler<>();
    }
}
