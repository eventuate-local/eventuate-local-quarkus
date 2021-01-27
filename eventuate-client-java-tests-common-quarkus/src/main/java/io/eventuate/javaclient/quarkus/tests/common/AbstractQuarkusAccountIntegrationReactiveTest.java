package io.eventuate.javaclient.quarkus.tests.common;

import io.eventuate.*;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

import static io.eventuate.testutil.AsyncUtil.await;

public class AbstractQuarkusAccountIntegrationReactiveTest extends AbstractQuarkusAccountIntegrationTest {

  @Inject
  @Named("EventuateAggregateStoreCrud")
  Instance<EventuateAggregateStoreCrud> aggregateStore;

  @Override
  protected <T extends Aggregate<T>> EntityIdAndVersion save(Class<T> classz, List<Event> events, Optional<SaveOptions> saveOptions) {
    return await(aggregateStore.get().save(classz, events, saveOptions));
  }

  @Override
  protected <T extends Aggregate<T>> EntityWithMetadata<T> find(Class<T> clasz, String entityId) {
    return await(aggregateStore.get().find(clasz, entityId));
  }
}