package io.eventuate.javaclient.quarkus.common.crud;

import io.eventuate.*;
import io.eventuate.javaclient.commonimpl.crud.AggregateCrud;
import io.eventuate.javaclient.commonimpl.crud.EventuateAggregateStoreCrudImpl;
import io.eventuate.javaclient.commonimpl.common.schema.EventuateEventSchemaManager;

import javax.enterprise.inject.Instance;
import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
public class EventuateCommonCrudConfiguration {

  @Singleton
  public SnapshotManager snapshotManager(Instance<SnapshotStrategy> snapshotStrategies) {
    SnapshotManagerImpl snapshotManager = new SnapshotManagerImpl();

    snapshotStrategies.stream().forEach(snapshotManager::addStrategy);

    return snapshotManager;
  }

  @Singleton
  public CompositeMissingApplyEventMethodStrategy compositeMissingApplyEventMethodStrategy(Instance<MissingApplyEventMethodStrategy> missingApplyEventMethodStrategies) {
    return new CompositeMissingApplyEventMethodStrategy(convertMissingApplyEventMethodStrategyInstanceToArray(missingApplyEventMethodStrategies));
  }

  @Singleton
  public EventuateAggregateStoreCrud eventuateAggregateStoreCrud(Instance<MissingApplyEventMethodStrategy> missingApplyEventMethodStrategies,
                                                         Instance<AggregateCrud> aggregateCrud,
                                                         SnapshotManager snapshotManager,
                                                         EventuateEventSchemaManager eventuateEventSchemaManager) {
    return new EventuateAggregateStoreCrudImpl(aggregateCrud.get(),
            snapshotManager,
            new CompositeMissingApplyEventMethodStrategy(convertMissingApplyEventMethodStrategyInstanceToArray(missingApplyEventMethodStrategies)),
            eventuateEventSchemaManager
    );
  }

  @Singleton
  public io.eventuate.sync.EventuateAggregateStoreCrud syncEventuateAggregateStoreCrud(Instance<MissingApplyEventMethodStrategy> missingApplyEventMethodStrategies,
                                                                           Instance<io.eventuate.javaclient.commonimpl.crud.sync.AggregateCrud> aggregateCrud,
                                                                           SnapshotManager snapshotManager) {

    io.eventuate.sync.EventuateAggregateStoreCrud eventuateAggregateStoreCrud =
            new io.eventuate.javaclient.commonimpl.crud.sync.EventuateAggregateStoreCrudImpl(aggregateCrud.get(),
            snapshotManager,
            new CompositeMissingApplyEventMethodStrategy(convertMissingApplyEventMethodStrategyInstanceToArray(missingApplyEventMethodStrategies)));

    return eventuateAggregateStoreCrud;
  }

  private MissingApplyEventMethodStrategy[] convertMissingApplyEventMethodStrategyInstanceToArray(Instance<MissingApplyEventMethodStrategy> missingApplyEventMethodStrategies) {
    return missingApplyEventMethodStrategies.stream().collect(Collectors.toList()).toArray(new MissingApplyEventMethodStrategy[] {});
  }
}
