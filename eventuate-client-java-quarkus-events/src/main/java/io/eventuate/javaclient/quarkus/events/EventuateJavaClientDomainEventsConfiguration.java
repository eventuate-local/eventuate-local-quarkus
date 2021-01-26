package io.eventuate.javaclient.quarkus.events;

import io.eventuate.EventuateAggregateStoreEvents;
import io.eventuate.SubscriptionsRegistry;
import io.eventuate.javaclient.domain.EventHandlerProcessor;
import io.eventuate.javaclient.domain.EventHandlerProcessorDispatchedEventReturningCompletableFuture;
import io.eventuate.javaclient.domain.EventHandlerProcessorDispatchedEventReturningVoid;
import io.eventuate.javaclient.eventdispatcher.EventDispatcherInitializer;

import javax.enterprise.inject.Instance;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Singleton
public class EventuateJavaClientDomainEventsConfiguration {

  @Singleton
  public EventDispatcherInitializer eventDispatcherInitializer(Instance<EventHandlerProcessor> processors, @Named("EventuateAggregateStoreEvents") Instance<EventuateAggregateStoreEvents> aggregateStore, SubscriptionsRegistry subscriptionsRegistry) {
    return new EventDispatcherInitializer(processors.stream().collect(Collectors.toList()).toArray(new EventHandlerProcessor[] {}),
            aggregateStore.get(), Executors.newCachedThreadPool(), subscriptionsRegistry);
  }

  @Singleton
  public SubscriptionsRegistry subscriptionsRegistry() {
    return new SubscriptionsRegistry();
  }

  @Singleton
  public EventHandlerProcessor eventHandlerProcessorDispatchedEventReturningVoid() {
    return new EventHandlerProcessorDispatchedEventReturningVoid();
  }

  @Singleton
  public EventHandlerProcessor eventHandlerProcessorDispatchedEventReturningCompletableFuture() {
    return new EventHandlerProcessorDispatchedEventReturningCompletableFuture();
  }
}
