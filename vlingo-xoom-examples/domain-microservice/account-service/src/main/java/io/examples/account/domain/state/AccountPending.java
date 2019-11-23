package io.examples.account.domain.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.StateTransition;
import io.vlingo.xoom.processor.TransitionHandler;

@Resource
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
        return "AccountPending{} " + super.toString();
    }
}
