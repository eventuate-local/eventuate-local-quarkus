package io.eventuate.local.java.quarkus.jdbc.events;

import io.eventuate.javaclient.commonimpl.common.adapters.AsyncToSyncTimeoutOptions;
import io.eventuate.javaclient.commonimpl.events.AggregateEvents;
import io.eventuate.javaclient.commonimpl.events.adapters.AsyncToSyncAggregateEventsAdapter;
import io.eventuate.local.java.events.EventuateKafkaAggregateSubscriptions;
import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumerConfigurationProperties;
import io.eventuate.messaging.kafka.basic.consumer.KafkaConsumerFactory;
import io.eventuate.messaging.kafka.common.EventuateKafkaConfigurationProperties;

import javax.enterprise.inject.Instance;
import javax.inject.Singleton;

@Singleton
public class EventuateLocalEventsConfiguration {
  @Singleton
  public EventuateKafkaAggregateSubscriptions aggregateEvents(Instance<EventuateKafkaConfigurationProperties> eventuateLocalAggregateStoreConfiguration,
                                                              Instance<EventuateKafkaConsumerConfigurationProperties> eventuateKafkaConsumerConfigurationProperties,
                                                              Instance<KafkaConsumerFactory> kafkaConsumerFactory) {
    return new EventuateKafkaAggregateSubscriptions(eventuateLocalAggregateStoreConfiguration.get(),
            eventuateKafkaConsumerConfigurationProperties.get(),
            kafkaConsumerFactory.get());
  }


  @Singleton
  public io.eventuate.javaclient.commonimpl.events.sync.AggregateEvents syncAggregateEvents(Instance<AsyncToSyncTimeoutOptions> timeoutOptions,
                                                                                            Instance<AggregateEvents> aggregateEvents) {
    AsyncToSyncAggregateEventsAdapter adapter = new AsyncToSyncAggregateEventsAdapter(aggregateEvents.get());
    if (!timeoutOptions.isUnsatisfied())
      adapter.setTimeoutOptions(timeoutOptions.get());
    return adapter;
  }
}
