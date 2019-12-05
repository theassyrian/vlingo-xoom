package io.examples.account.application;

import io.examples.account.domain.model.AccountQuery;
import io.reactivex.Single;
import io.vlingo.actors.Stoppable;
import io.vlingo.common.Completes;

public interface Account extends Stoppable {
    Completes<Single<AccountQuery>> query(Long id);
}
