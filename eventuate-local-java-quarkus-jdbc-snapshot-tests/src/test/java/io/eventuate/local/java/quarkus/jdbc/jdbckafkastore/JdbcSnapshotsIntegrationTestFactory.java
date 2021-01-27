package io.eventuate.local.java.quarkus.jdbc.jdbckafkastore;

import io.eventuate.example.banking.domain.Account;
import io.eventuate.example.banking.domain.AccountCommand;
import io.eventuate.example.banking.domain.AccountSnapshotStrategy;
import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStoreCrud;

import javax.inject.Named;
import javax.inject.Singleton;

import static org.mockito.Mockito.spy;

@Singleton
public class JdbcSnapshotsIntegrationTestFactory {

  @Singleton
  public AccountSnapshotStrategy accountSnapshotStrategy() {
    return spy(AccountSnapshotStrategy.class);
  }

  @Singleton
  public AggregateRepository<Account, AccountCommand> accountRepositorySync(@Named("syncEventuateAggregateStoreCrud") EventuateAggregateStoreCrud aggregateStore) {
    return new AggregateRepository<>(Account.class, aggregateStore);
  }
}
