package io.vlingo.xoom.actors.account.event;


import io.vlingo.xoom.actors.account.state.AccountState;
import io.vlingo.xoom.actors.processor.Event;

public class AccountEvent extends Event<AccountState> {

    public AccountEvent(AccountState source, AccountState target) {
        super(source, target);
    }
}
