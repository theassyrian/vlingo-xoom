package io.vlingo.xoom.actors.account.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.processor.StateTransition;
import io.vlingo.xoom.actors.processor.TransitionHandler;

public class AccountCreated extends AccountState<AccountCreated> {
    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_CREATED;

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                TransitionHandler.handleFor(AccountCreated.class, AccountPending.class)
                        .withPath(AccountPending.TYPE.name())
                        .withTransition(this::toPending)
        };
    }

    private Completes<StateTransition<AccountCreated, AccountPending>> toPending() {
        return StateTransition.transition(this, new AccountPending())
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
        return "AccountCreated{} " + super.toString();
    }
}
