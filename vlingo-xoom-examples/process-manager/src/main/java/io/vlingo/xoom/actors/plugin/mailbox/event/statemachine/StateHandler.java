package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine;

import io.vlingo.common.Completes;

public interface StateHandler<T extends State, R extends State> {
    Completes<StateTransition<T, R>> execute();
}
