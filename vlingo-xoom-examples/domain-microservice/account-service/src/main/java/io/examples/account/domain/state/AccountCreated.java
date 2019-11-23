package io.examples.account.domain.state;

import io.vlingo.common.Completes;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.StateTransition;
import io.vlingo.xoom.processor.TransitionHandler;

@Resource
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
        return "AccountCreated{} " + super.toString();
    }
}
