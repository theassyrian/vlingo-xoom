package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.StateTransition;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler;

import static io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler.handleFor;

public class AccountArchived extends AccountState<AccountArchived> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_ARCHIVED;

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handleFor(AccountArchived.class, AccountActivated.class)
                        .withPath(AccountActivated.TYPE.name())
                        .withTransition(this::toActivated)
        };
    }

    private Completes<StateTransition<AccountArchived, AccountActivated>> toActivated() {
        return Completes.withSuccess(new StateTransition<>(this, new AccountActivated()));
    }

    @Override
    public String toString() {
        return "AccountArchived{} " + super.toString();
    }
}