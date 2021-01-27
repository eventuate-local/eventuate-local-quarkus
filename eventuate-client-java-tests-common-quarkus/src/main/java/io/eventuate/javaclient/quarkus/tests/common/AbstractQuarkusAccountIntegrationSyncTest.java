package io.eventuate.javaclient.quarkus.tests.common;

import io.eventuate.*;
import io.eventuate.sync.EventuateAggregateStoreCrud;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

public class AbstractQuarkusAccountIntegrationSyncTest extends AbstractQuarkusAccountIntegrationTest {

  @Inject
  @Named("syncEventuateAggregateStoreCrud")
  Instance<EventuateAggregateStoreCrud> aggregateStore;

  @Override
  protected <T extends Aggregate<T>> EntityIdAndVersion save(Class<T> classz, List<Event> events, Optional<SaveOptions> saveOptions) {
    return aggregateStore.get().save(classz, events, saveOptions);
  }

  @Override
  protected <T extends Aggregate<T>> EntityWithMetadata<T> find(Class<T> clasz, String entityId) {
    return aggregateStore.get().find(clasz, entityId);
  }
}
