package io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.StateTransition;
import io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler;

import static io.vlingo.xoom.actors.plugin.mailbox.event.statemachine.TransitionHandler.handleFor;

public class AccountActivated extends AccountState<AccountActivated> {

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handleFor(AccountActivated.class, AccountArchived.class)
                        .withPath(String.valueOf(AccountArchived.TYPE)).withTransition(this::handleArchived),
                handleFor(AccountActivated.class, AccountSuspended.class)
                        .withPath(String.valueOf(AccountSuspended.TYPE)).withTransition(this::handleSuspended)
        };
    }

    private Completes<StateTransition<AccountActivated, AccountArchived>> handleArchived() {
        return StateTransition.transition(this, new AccountArchived())
                .onSuccess(Completes::withSuccess)
                .onError(Completes::withFailure)
                .apply();
    }

    private Completes<StateTransition<AccountActivated, AccountSuspended>> handleSuspended() {
        return StateTransition.transition(this, new AccountSuspended())
                .onSuccess(Completes::withSuccess)
                .onError(Completes::withFailure)
                .apply();
    }

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_ACTIVATED;

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public String toString() {
        return "AccountActivated{} " + super.toString();
    }
}
