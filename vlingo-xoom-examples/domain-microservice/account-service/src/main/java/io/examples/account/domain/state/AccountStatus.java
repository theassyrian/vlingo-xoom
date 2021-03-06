package io.examples.account.domain.state;

import io.examples.account.domain.Account;
import io.vlingo.xoom.stepflow.State;

/**
 * The {@link AccountStatus} represents the multiple logical states of an {@link Account}. If an
 * update is applied to an {@link Account} that does not have a logical state, then the version
 * of its current {@link State} will be incremented.
 * <p>
 * For example, if you want to update an
 * {@link Account}'s accountNumber, then you can specify that the account must be in an ACCOUNT_ACTIVATED
 * state. All that is required is to implement an event handler with the path,
 * "ACCOUNT_ACTIVATED::ACCOUNT_ACTIVATED::ACCOUNT_NUMBER_UPDATED".
 */
public enum AccountStatus {
    ACCOUNT_CREATED,
    ACCOUNT_PENDING,
    ACCOUNT_CONFIRMED,
    ACCOUNT_SUSPENDED,
    ACCOUNT_ARCHIVED,
    ACCOUNT_ACTIVATED,
    ACCOUNT_NUMBER_UPDATED;

    @Override
    public String toString() {
        return this.name();
    }
}
