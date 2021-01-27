package io.eventuate.javaclient.quarkus.crud;

import io.eventuate.EventuateAggregateStoreCrud;
import io.eventuate.javaclient.domain.EventHandlerProcessor;
import io.eventuate.javaclient.domain.EventHandlerProcessorEventHandlerContextReturningCompletableFuture;
import io.eventuate.javaclient.domain.EventHandlerProcessorEventHandlerContextReturningVoid;

import javax.enterprise.inject.Instance;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class EventuateJavaClientDomainCrudConfiguration {

  @Singleton
  public EventHandlerProcessor eventHandlerProcessorEventHandlerContextReturningVoid(@Named("EventuateAggregateStoreCrud") Instance<EventuateAggregateStoreCrud> aggregateStore) {
    return new EventHandlerProcessorEventHandlerContextReturningVoid(aggregateStore.get());
  }

  @Singleton
  public EventHandlerProcessor eventHandlerProcessorEventHandlerContextReturningCompletableFuture(@Named("EventuateAggregateStoreCrud") Instance<EventuateAggregateStoreCrud> aggregateStore) {
    return new EventHandlerProcessorEventHandlerContextReturningCompletableFuture(aggregateStore.get());
  }

}
