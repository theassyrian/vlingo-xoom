package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state;

import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.State;

public abstract class AccountState<T extends State> extends State<T> {
    public AccountStatus getType() {
        return AccountStatus.valueOf(getName());
    }

    @Override
    public String toString() {
        return "AccountState{} " + super.toString();
    }
}
