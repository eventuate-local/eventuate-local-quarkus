package io.eventuate.javaclient.quarkus.common.crud;

import io.eventuate.CompositeMissingApplyEventMethodStrategy;
import io.eventuate.EventuateAggregateStoreCrud;
import io.eventuate.MissingApplyEventMethodStrategy;
import io.eventuate.SnapshotManager;
import io.eventuate.SnapshotManagerImpl;
import io.eventuate.SnapshotStrategy;
import io.eventuate.javaclient.commonimpl.common.schema.EventuateEventSchemaManager;
import io.eventuate.javaclient.commonimpl.crud.AggregateCrud;
import io.eventuate.javaclient.commonimpl.crud.EventuateAggregateStoreCrudImpl;

import javax.enterprise.inject.Instance;
import javax.inject.Named;
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
  public @Named("EventuateAggregateStoreCrud") EventuateAggregateStoreCrud eventuateAggregateStoreCrud(Instance<CompositeMissingApplyEventMethodStrategy> missingApplyEventMethodStrategies,
                                                         Instance<AggregateCrud> aggregateCrud,
                                                         SnapshotManager snapshotManager,
                                                         Instance<EventuateEventSchemaManager> eventuateEventSchemaManager) {
    return new EventuateAggregateStoreCrudImpl(aggregateCrud.get(),
            snapshotManager,
            missingApplyEventMethodStrategies.get().toMissingApplyEventMethodStrategy(),
            eventuateEventSchemaManager.get()
    );
  }

  @Singleton
  public @Named("syncEventuateAggregateStoreCrud") io.eventuate.sync.EventuateAggregateStoreCrud syncEventuateAggregateStoreCrud(Instance<CompositeMissingApplyEventMethodStrategy> missingApplyEventMethodStrategies,
                                                                                                                             Instance<io.eventuate.javaclient.commonimpl.crud.sync.AggregateCrud> aggregateCrud,
                                                                                                                             SnapshotManager snapshotManager) {

    io.eventuate.sync.EventuateAggregateStoreCrud eventuateAggregateStoreCrud =
            new io.eventuate.javaclient.commonimpl.crud.sync.EventuateAggregateStoreCrudImpl(aggregateCrud.get(),
            snapshotManager,
            missingApplyEventMethodStrategies.get().toMissingApplyEventMethodStrategy());

    return eventuateAggregateStoreCrud;
  }

  private static MissingApplyEventMethodStrategy[] convertMissingApplyEventMethodStrategyInstanceToArray(Instance<MissingApplyEventMethodStrategy> missingApplyEventMethodStrategies) {
    return missingApplyEventMethodStrategies.stream().collect(Collectors.toList()).toArray(new MissingApplyEventMethodStrategy[0]);
  }
}
