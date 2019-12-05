package io.examples.account.application;

import io.examples.account.domain.model.AccountQuery;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@Client(path = "/v1/accounts", id = "account-service")
public interface AccountClient {

    @Get("/{id}")
    Single<AccountQuery> queryAccount(Long id);
}
