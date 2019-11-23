package io.examples.account.domain.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.StateTransition;
import io.vlingo.xoom.processor.TransitionHandler;

@Resource
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
                .onSuccess(log())
                .onError((transition, state) -> Completes.withFailure(transition))
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
