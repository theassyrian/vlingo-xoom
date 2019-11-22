package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.StateTransition;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler;

import static io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler.handleFor;

public class AccountConfirmed extends AccountState<AccountConfirmed> {
    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_CONFIRMED;

    public AccountConfirmed() {
    }

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handleFor(AccountConfirmed.class, AccountActivated.class)
                        .withPath(AccountActivated.TYPE.name())
                        .withTransition(this::toActive)
        };
    }

    private Completes<StateTransition<AccountConfirmed, AccountActivated>> toActive() {
        return Completes.withSuccess(new StateTransition<>(this, new AccountActivated()));
    }

    @Override
    public String toString() {
        return "AccountConfirmed{} " + super.toString();
    }
}
