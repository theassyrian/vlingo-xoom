package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.StateTransition;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler;

import static io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler.handleFor;

public class AccountPending extends AccountState<AccountPending> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_PENDING;

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handleFor(AccountPending.class, AccountConfirmed.class)
                        .withPath(AccountConfirmed.TYPE.name())
                        .withTransition(this::toConfirmed)
        };
    }

    private Completes<StateTransition<AccountPending, AccountConfirmed>> toConfirmed() {
        return Completes.withSuccess(new StateTransition<>(this, new AccountConfirmed()));
    }

    @Override
    public String toString() {
        return "AccountPending{} " + super.toString();
    }
}
