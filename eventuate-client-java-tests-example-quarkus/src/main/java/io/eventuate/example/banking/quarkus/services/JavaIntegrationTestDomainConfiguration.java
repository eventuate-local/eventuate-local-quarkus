package io.eventuate.example.banking.quarkus.services;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStoreCrud;
import io.eventuate.example.banking.domain.Account;
import io.eventuate.example.banking.domain.AccountCommand;
import io.eventuate.example.banking.services.AccountCommandSideEventHandler;
import io.eventuate.example.banking.services.AccountQuerySideEventHandler;
import io.eventuate.example.banking.services.AccountService;
import io.eventuate.example.banking.services.MoneyTransferCommandSideEventHandler;
import io.eventuate.example.banking.services.counting.InvocationCounter;

import javax.enterprise.inject.Instance;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class JavaIntegrationTestDomainConfiguration {

  @Singleton
  public AccountCommandSideEventHandler accountCommandSideEventHandler() {
    return new AccountCommandSideEventHandler();
  }

  @Singleton
  public MoneyTransferCommandSideEventHandler moneyTransferCommandSideEventHandler(InvocationCounter invocationCounter) {
    return new MoneyTransferCommandSideEventHandler(invocationCounter);
  }

  @Singleton
  public AccountQuerySideEventHandler accountQuerySideEventHandler() {
    return new AccountQuerySideEventHandler();
  }

  @Singleton
  public AccountService accountService(AggregateRepository<Account, AccountCommand> accountRepository) {
    return new AccountService(accountRepository);
  }

  @Singleton
  public AggregateRepository<Account, AccountCommand> accountRepository(@Named("EventuateAggregateStoreCrud") Instance<EventuateAggregateStoreCrud> aggregateStore) {
    return new AggregateRepository<>(Account.class, aggregateStore.get());
  }

  @Singleton
  public InvocationCounter invocationCounter() {
    return new InvocationCounter();
  }
}
