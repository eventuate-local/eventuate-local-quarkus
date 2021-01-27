package io.eventuate.local.java.quarkus.jdbc.events;

import io.quarkus.runtime.Startup;

import javax.annotation.PreDestroy;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Startup
public class EventuateKafkaAggregateSubscriptionCleaner {

  @Inject
  Instance<EventuateKafkaAggregateSubscriptionCleaner> eventuateKafkaAggregateSubscriptionCleaners;

  @PreDestroy
  public void clean() {
    eventuateKafkaAggregateSubscriptionCleaners.select().forEach(EventuateKafkaAggregateSubscriptionCleaner::clean);
  }

}
