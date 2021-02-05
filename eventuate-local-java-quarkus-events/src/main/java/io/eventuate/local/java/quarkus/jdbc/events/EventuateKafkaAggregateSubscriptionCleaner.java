package io.eventuate.local.java.quarkus.jdbc.events;

import io.eventuate.local.java.events.EventuateKafkaAggregateSubscriptions;
import io.quarkus.runtime.Startup;

import javax.annotation.PreDestroy;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Startup
public class EventuateKafkaAggregateSubscriptionCleaner {

  @Inject
  Instance<EventuateKafkaAggregateSubscriptions> eventuateKafkaAggregateSubscriptionCleaners;

  @PreDestroy
  public void clean() {
    eventuateKafkaAggregateSubscriptionCleaners.stream().forEach(EventuateKafkaAggregateSubscriptions::cleanUp);
  }

}
