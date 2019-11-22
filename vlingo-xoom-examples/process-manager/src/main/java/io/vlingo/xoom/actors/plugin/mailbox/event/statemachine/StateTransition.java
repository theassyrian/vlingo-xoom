package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.common.Completes;

import java.util.function.Function;

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
    private CompletesState<T, R> successResult;
    private CompletesState<T, R> errorResult;

    public StateTransition(T from, R to) {
        this.from = from;
        this.to = to;
    }

    public Completes<R> apply(Function<T, R> function) {
        if (successResult == null || errorResult == null) {
            throw new IllegalStateException("A state transition must define a success and error result");
        }

        return Completes.withSuccess(function.apply(from));
    }

    public Completes<StateTransition<T, R>> apply() {
        if (successResult == null || errorResult == null) {
            throw new IllegalStateException("A state transition must define a success and error result");
        }

        return successResult.apply(this)
                .recoverFrom((e -> errorResult.apply(this).outcome()));
    }

    public StateTransition<T, R> onSuccess(CompletesState<T, R> action) {
        this.successResult = action;
        return this;
    }

    public StateTransition<T, R> onError(CompletesState<T, R> action) {
        this.errorResult = action;
        return this;
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

    public static <T1 extends State, R1 extends State> StateTransition<T1, R1> transition(T1 source, R1 target) {
        return new StateTransition<>(source, target);
    }
}
