package io.examples.account.domain;

import io.examples.account.endpoint.AccountEndpoint;
import io.examples.account.endpoint.v1.AccountResource;
import io.examples.account.processor.ProcessorContext;
import io.examples.account.repository.AccountRepository;
import io.reactivex.Observable;
import io.vlingo.common.Completes;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Consumer;

/**
 * The {@link AccountService} exposes operations and business logic that pertains to the {@link Account} entity and
 * aggregate root. This service forms an anti-corruption layer that is exposed to consumers using the
 * {@link AccountResource}.
 *
 * @author Kenny Bastani
 * @see AccountEndpoint
 */
@Singleton
public class AccountService {

    private final AccountRepository accountRepository;
    private final ProcessorContext accountProcessor;

    public AccountService(AccountRepository accountRepository, ProcessorContext processorContext) {
        this.accountRepository = accountRepository;
        this.accountProcessor = processorContext;
    }

    /**
     * Get all {@link Account}s from the {@link AccountRepository}.
     *
     * @return a list of {@link Account} entities.
     */
    public Completes<List<Account>> getAccounts() {
        return Completes.withSuccess(accountRepository.findAll())
                .andThen(accounts -> Observable.fromIterable(accounts).toList().blockingGet());
    }

    /**
     * Lookup an {@link Account} by its unique identifier.
     *
     * @param id is the unique identifier of the {@link Account}.
     * @return the {@link Account} or throw a {@link RuntimeException} if one does not exist.
     */
    public Completes<Account> getAccount(Long id) {
        return accountRepository.findById(id).map(Completes::withSuccess).orElseThrow(() ->
                new RuntimeException("Account with id[" + id + "] does not exist"));
    }

    /**
     * Register a new {@link Account}.
     *
     * @param account is the {@link Account} to create.
     * @return the created {@link Account}.
     */
    public Completes<Account> createAccount(Account account) {
        account = accountRepository.save(account);
        execute(account.getId(), result -> result.create(accountProcessor.getProcessor()));
        return getAccount(account.getId());
    }

    /**
     * Updates the {@link Account} with the corresponding unique identifier.
     *
     * @param id      is the unique identifier for the {@link Account}.
     * @param account is the {@link Account} entity containing the fields to update.
     * @return the updated {@link Account}.
     */
    public Completes<Account> updateAccount(@NotNull Long id, @NotNull Account account) {
        accountRepository.update(id, account.getAccountNumber());
        return getAccount(id);
    }

    /**
     * Delete the {@link Account} with the corresponding unique identifier.
     *
     * @param id is the unique identifier of the {@link Account} that is to be deleted.
     */
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Completes<Account> confirmAccount(@NotNull Long id) {
        return execute(id, account -> account.confirm(accountProcessor.getProcessor()));
    }

    public Completes<Account> activateAccount(@NotNull Long id) {
        return execute(id, account -> account.activate(accountProcessor.getProcessor()));
    }

    public Completes<Account> suspendAccount(@NotNull Long id) {
        return execute(id, account -> account.suspend(accountProcessor.getProcessor()));
    }

    public Completes<Account> archiveAccount(@NotNull Long id) {
        return execute(id, account -> account.archive(accountProcessor.getProcessor()));
    }

    private Completes<Account> execute(@NotNull Long id, Consumer<Account> commandHandler) {
        return getAccount(id)
                .andThenConsume(commandHandler)
                .andThenConsume(account ->
                        accountRepository.update(id, account.getAccountStatus(), account.getVersion()))
                .andThenConsume(Completes::withSuccess)
                .otherwiseConsume(Completes::withFailure);
    }
}
