package io.vlingo.xoom.actors.account.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.processor.StateTransition;
import io.vlingo.xoom.actors.processor.TransitionHandler;

public class AccountArchived extends AccountState<AccountArchived> {

    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_ARCHIVED;

    @Override
    public String getName() {
        return TYPE.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                TransitionHandler.handleFor(AccountArchived.class, AccountActivated.class)
                        .withPath(AccountActivated.TYPE.name())
                        .withTransition(this::toActivated)
        };
    }

    private Completes<StateTransition<AccountArchived, AccountActivated>> toActivated() {
        return StateTransition.transition(this, new AccountActivated())
                .onSuccess(transition -> {
                    log.info("Transitioned from [" + transition.getSourceName() + "] to " +
                            "[" + transition.getTargetName() + "]");

                    return Completes.withSuccess(transition);
                })
                .onError(Completes::withFailure)
                .build();
    }

    @Override
    public String toString() {
        return "AccountArchived{} " + super.toString();
    }
}