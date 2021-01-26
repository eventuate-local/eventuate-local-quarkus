package io.eventuate.javaclient.quarkus.common.crud;

import io.eventuate.AggregateRepository;
import io.eventuate.CompositeMissingApplyEventMethodStrategy;
import io.quarkus.runtime.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Startup
public class AggregateRepositoryInitializer {

  @Inject
  CompositeMissingApplyEventMethodStrategy strategies;

  @Inject
  Instance<AggregateRepository> aggregateRepositories;

  @Inject
  Instance<io.eventuate.sync.AggregateRepository> syncAggregateRepositories;

  @PostConstruct
  public void setMissingStrategies() {
    aggregateRepositories.stream().forEach(aggregateRepository -> aggregateRepository.setMissingApplyEventMethodStrategy(strategies));
    syncAggregateRepositories.stream().forEach(aggregateRepository -> aggregateRepository.setMissingApplyEventMethodStrategy(strategies));
  }
}
