package io.vlingo.xoom.actors.account.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.actors.processor.StateTransition;
import io.vlingo.xoom.actors.processor.TransitionHandler;

public class AccountConfirmed extends AccountState<AccountConfirmed> {
    public static final AccountStatus TYPE = AccountStatus.ACCOUNT_CONFIRMED;

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                TransitionHandler.handleFor(AccountConfirmed.class, AccountActivated.class)
                        .withPath(AccountActivated.TYPE.name())
                        .withTransition(this::toActive)
        };
    }

    private Completes<StateTransition<AccountConfirmed, AccountActivated>> toActive() {
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
    public String getName() {
        return TYPE.name();
    }

    @Override
    public String toString() {
        return "AccountConfirmed{} " + super.toString();
    }
}
