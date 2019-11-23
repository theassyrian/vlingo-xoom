package io.examples.account.domain.event;

import io.examples.account.domain.state.AccountStatus;
import io.vlingo.xoom.processor.Event;

public class AccountEvent extends Event {

    public AccountEvent(AccountStatus source, AccountStatus target) {
        super(source.name(), target.name());
    }
}
