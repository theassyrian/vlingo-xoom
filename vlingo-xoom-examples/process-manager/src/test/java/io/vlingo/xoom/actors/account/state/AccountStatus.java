package io.vlingo.xoom.actors.account.state;

public enum AccountStatus {
    ACCOUNT_CREATED,
    ACCOUNT_PENDING,
    ACCOUNT_CONFIRMED,
    ACCOUNT_SUSPENDED,
    ACCOUNT_ARCHIVED,
    ACCOUNT_ACTIVATED;

    @Override
    public String toString() {
        return this.name();
    }
}
