package io.vlingo.xoom.actors.account.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.processor.StateTransition;
import io.vlingo.xoom.actors.processor.TransitionHandler;


public class AccountActivated extends AccountState<AccountActivated> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_ACTIVATED;

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                TransitionHandler.handleFor(AccountActivated.class, AccountArchived.class)
                        .withPath(String.valueOf(AccountArchived.TYPE)).withTransition(this::handleArchived),
                TransitionHandler.handleFor(AccountActivated.class, AccountSuspended.class)
                        .withPath(String.valueOf(AccountSuspended.TYPE)).withTransition(this::handleSuspended)
        };
    }

    private Completes<StateTransition<AccountActivated, AccountArchived>> handleArchived() {
        return StateTransition.transition(this, new AccountArchived())
                .onSuccess(transition -> {
                    log.info("Transitioned from [" + transition.getSourceName() + "] to " +
                            "[" + transition.getTargetName() + "]");

                    return Completes.withSuccess(transition);
                })
                .onError(Completes::withFailure)
                .build();
    }

    private Completes<StateTransition<AccountActivated, AccountSuspended>> handleSuspended() {
        return StateTransition.transition(this, new AccountSuspended())
                .onSuccess(transition -> {
                    log.info("Transitioned from [" + transition.getSourceName() + "] to " +
                            "[" + transition.getTargetName() + "]");

                    return Completes.withSuccess(transition);
                })
                .onError(Completes::withFailure)
                .build();
    }

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public String toString() {
        return "AccountActivated{} " + super.toString();
    }
}
