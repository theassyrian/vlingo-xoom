package io.examples.account.domain;

import io.examples.account.data.Identity;
import io.examples.account.domain.event.AccountEvent;
import io.examples.account.domain.state.*;
import io.vlingo.xoom.processor.Processor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The {@link Account} is the entity representation of an aggregate for a customer's account. This class is used
 * for both persistence and business logic that is exposed and controlled from the {@link AccountService}.
 *
 * @author Kenny Bastani
 */
@Entity
public class Account extends Identity {

    private String accountNumber;
    private AccountStatus accountStatus = AccountStatus.ACCOUNT_CREATED;
    private String version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addresses = new HashSet<>();

    public Account() {
    }

    /**
     * Instantiates a new {@link Account} entity.
     *
     * @param accountNumber is the customer's account number.
     * @param addresses     is a set of addresses for the customer's account.
     */
    public Account(String accountNumber, Set<Address> addresses) {
        this.accountNumber = accountNumber;
        this.addresses.addAll(addresses);
    }

    public String getVersion() {
        return version;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    /**
     * Instantiates a new {@link Account} entity.
     *
     * @param accountNumber is the customer's account number.
     */
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Get the account number for this customer's account.
     *
     * @return a unique account number for the customer's account.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Set the account number for this customer's account.
     *
     * @param accountNumber is a unique account number for the customer's account.
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Get the collection of {@link CreditCard}s for this customer's account.
     *
     * @return a set of {@link CreditCard}s for this customer's account.
     */
    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    /**
     * Set the collection of {@link CreditCard}s for this customer's account.
     *
     * @param creditCards is a set of {@link CreditCard}s for this customer's account.
     */
    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    /**
     * Get a collection of {@link Address}es for this customer's account.
     *
     * @return a set of addresses for this customer's account.
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     * Set a collection of {@link Address}es for this customer's account.
     *
     * @param addresses is a set of addresses for this customer's account.
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Account create(Processor processor) {
        return sendEvent(processor, new AccountPending());
    }

    public Account confirm(Processor processor) {
        return sendEvent(processor, new AccountConfirmed());
    }

    public Account activate(Processor processor) {
        return sendEvent(processor, new AccountActivated());
    }

    public Account suspend(Processor processor) {
        return sendEvent(processor, new AccountSuspended());
    }

    public Account archive(Processor processor) {
        return sendEvent(processor, new AccountArchived());
    }

    private Account sendEvent(Processor processor, AccountState targetState) {
        AccountEvent event = new AccountEvent(accountStatus, targetState.getType());
        return Optional.ofNullable(processor.applyEvent(event)
                .andThenConsume(stateTransition -> {
                    stateTransition.apply(targetState).await();
                    this.accountStatus = AccountStatus.valueOf(stateTransition.getTargetName());
                    this.version = targetState.getVersion().toString();
                }).otherwise(transition -> null).await())
                .map(transition -> this)
                .orElseThrow(() ->
                        new RuntimeException("The event with type [" + event.getEventType() + "] does not match a" +
                                " valid transition handler in the processor kernel."));
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountStatus=" + accountStatus +
                ", version='" + version + '\'' +
                ", creditCards=" + creditCards +
                ", addresses=" + addresses +
                "} " + super.toString();
    }
}