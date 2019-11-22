package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.common.Completes;

/**
 * A functional interface that transforms a {@link StateTransition} into a {@link Completes}.
 *
 * @param <T> is the source {@link State}
 * @param <R> is the target {@link State}
 * @author Kenny Bastani
 */
public interface CompletesState<T extends State, R extends State> {
    Completes<StateTransition<T, R>> apply(StateTransition<T, R> transition);
}
