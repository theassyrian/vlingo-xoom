package io.vlingo.xoom.actors.account.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.processor.StateTransition;
import io.vlingo.xoom.actors.processor.TransitionHandler;

public class AccountPending extends AccountState<AccountPending> {
    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_PENDING;

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                TransitionHandler.handleFor(AccountPending.class, AccountConfirmed.class)
                        .withPath(AccountConfirmed.TYPE.name())
                        .withTransition(this::toConfirmed)
        };
    }

    private Completes<StateTransition<AccountPending, AccountConfirmed>> toConfirmed() {
        return StateTransition.transition(this, new AccountConfirmed())
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
        return "AccountPending{} " + super.toString();
    }
}
