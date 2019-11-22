package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.StateTransition;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler;

import static io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler.handleFor;

public class AccountCreated extends AccountState<AccountCreated> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_CREATED;

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handleFor(AccountCreated.class, AccountPending.class)
                        .withPath(AccountPending.TYPE.name())
                        .withTransition(this::toPending)
        };
    }

    private Completes<StateTransition<AccountCreated, AccountPending>> toPending() {
        return Completes.withSuccess(new StateTransition<>(this, new AccountPending()));
    }

    @Override
    public String toString() {
        return "AccountCreated{} " + super.toString();
    }
}
