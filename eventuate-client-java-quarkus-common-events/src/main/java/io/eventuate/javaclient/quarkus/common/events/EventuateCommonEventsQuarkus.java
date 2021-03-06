package io.eventuate.javaclient.quarkus.common.events;

import io.eventuate.EventuateAggregateStoreEvents;
import io.eventuate.javaclient.commonimpl.common.schema.EventuateEventSchemaManager;
import io.eventuate.javaclient.commonimpl.events.AggregateEvents;
import io.eventuate.javaclient.commonimpl.events.EventuateAggregateStoreEventsImpl;
import io.eventuate.javaclient.commonimpl.events.SerializedEventDeserializer;

import javax.enterprise.inject.Instance;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class EventuateCommonEventsQuarkus {

  @Singleton
  public @Named("EventuateAggregateStoreEvents") EventuateAggregateStoreEvents eventuateAggregateStoreEvents(Instance<AggregateEvents> aggregateEvents,
                                                                                                             Instance<EventuateEventSchemaManager> eventuateEventSchemaManager,
                                                                                                             Instance<SerializedEventDeserializer> serializedEventDeserializer) {
    if (serializedEventDeserializer.stream().count() > 1)
      throw new RuntimeException("Expected no more than 1 SerializedEventDeserializer");

    EventuateAggregateStoreEventsImpl eventuateAggregateStoreEvents =
            new EventuateAggregateStoreEventsImpl(aggregateEvents.get(), eventuateEventSchemaManager.get());

    serializedEventDeserializer.stream().findFirst().ifPresent(eventuateAggregateStoreEvents::setSerializedEventDeserializer);

    return eventuateAggregateStoreEvents;
  }

  @Singleton
  public @Named("syncEventuateAggregateStoreEvents") io.eventuate.sync.EventuateAggregateStoreEvents syncEventuateAggregateStoreEvents(Instance<io.eventuate.javaclient.commonimpl.events.sync.AggregateEvents> aggregateEvents,
                                                                                                                                   Instance<SerializedEventDeserializer> serializedEventDeserializer) {
    if (serializedEventDeserializer.stream().count() > 1)
      throw new RuntimeException("Expected no more than 1 SerializedEventDeserializer");

    io.eventuate.javaclient.commonimpl.events.sync.EventuateAggregateStoreEventsImpl eventuateAggregateStoreEvents =
            new io.eventuate.javaclient.commonimpl.events.sync.EventuateAggregateStoreEventsImpl(aggregateEvents.get());

    serializedEventDeserializer.stream().findFirst().ifPresent(eventuateAggregateStoreEvents::setSerializedEventDeserializer);

    return eventuateAggregateStoreEvents;
  }
}
