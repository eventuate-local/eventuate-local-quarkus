package io.eventuate.javaclient.quarkus.common;

import io.eventuate.EventuateAggregateStore;
import io.eventuate.EventuateAggregateStoreCrud;
import io.eventuate.EventuateAggregateStoreEvents;
import io.eventuate.javaclient.commonimpl.EventuateAggregateStoreImpl;

import javax.enterprise.inject.Instance;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class EventuateCommonConfiguration {

  @Singleton
  public EventuateAggregateStore aggregateEventStore(@Named("EventuateAggregateStoreCrud") Instance<EventuateAggregateStoreCrud> eventuateAggregateStoreCrud,
                                                     @Named("EventuateAggregateStoreEvents") Instance<EventuateAggregateStoreEvents> eventuateAggregateStoreEvents) {

    return new EventuateAggregateStoreImpl(eventuateAggregateStoreCrud.get(), eventuateAggregateStoreEvents.get());
  }

  @Singleton
  public io.eventuate.sync.EventuateAggregateStore syncAggregateEventStore(@Named("syncEventuateAggregateStoreCrud") Instance<io.eventuate.sync.EventuateAggregateStoreCrud> eventuateAggregateStoreCrud,
                                                                           @Named("syncEventuateAggregateStoreEvents") Instance<io.eventuate.sync.EventuateAggregateStoreEvents> eventuateAggregateStoreEvents) {

    return new io.eventuate.javaclient.commonimpl.sync.EventuateAggregateStoreImpl(eventuateAggregateStoreCrud.get(), eventuateAggregateStoreEvents.get());
  }
}
