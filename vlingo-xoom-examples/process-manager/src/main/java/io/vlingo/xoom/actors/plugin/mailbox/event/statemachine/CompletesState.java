package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.common.Completes;

public interface CompletesState<T extends State, R extends State> {
    Completes<StateTransition<T, R>> apply(StateTransition<T, R> transition);
}
