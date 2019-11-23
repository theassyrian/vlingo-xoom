package io.vlingo.xoom.actors.account.state;

import io.vlingo.actors.Logger;
import io.vlingo.xoom.actors.processor.State;

public abstract class AccountState<T extends State> extends State<T> {
    final Logger log = Logger.basicLogger();
    public AccountStatus getType() {
        return AccountStatus.valueOf(getName());
    }

    @Override
    public String toString() {
        return "AccountState{} " + super.toString();
    }
}
