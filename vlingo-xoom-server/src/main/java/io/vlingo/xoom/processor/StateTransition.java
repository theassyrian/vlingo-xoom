package io.vlingo.xoom.processor;

import io.vlingo.common.Completes;

import java.util.function.BiConsumer;

/**
 * A {@link StateTransition} is a resource specification that defines an input state and output state, while providing
 * a validation error if an input state cannot progress to an output state.
 *
 * @param <T> is the source state
 * @param <R> is the target state
 * @author Kenny Bastani
 */
public class StateTransition<T extends State, R extends State> implements Transition {

    private T from;
    private R to;
    private BiConsumer<T, R> action;

    public StateTransition(T from, R to) {
        this.from = from;
        this.to = to;
    }

    public Completes<StateTransition<T, R>> apply(R state) {
        if (action == null) {
            throw new IllegalStateException("A state transition must define a success and error result");
        }

        return Completes.withSuccess(this)
                .andThenConsume(t -> action.accept(from, to));
    }

    public void setActionHandler(BiConsumer<T, R> action) {
        this.action = action;
    }

    public T getFrom() {
        return from;
    }

    public R getTo() {
        return to;
    }

    @Override
    public String getSourceName() {
        return getFrom().getName();
    }

    @Override
    public String getTargetName() {
        return getTo().getName();
    }

    @Override
    public String toString() {
        return "StateTransition{" +
                "from=" + from +
                ", to=" + to +
                ", action=" + action +
                '}';
    }
}
