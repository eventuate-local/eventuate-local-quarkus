package io.eventuate.javaclient.quarkus.jdbc;

import io.eventuate.common.inmemorydatabase.EventuateDatabaseScriptSupplier;
import io.eventuate.javaclient.commonimpl.events.AggregateEvents;
import io.eventuate.javaclient.commonimpl.events.adapters.SyncToAsyncAggregateEventsAdapter;
import io.eventuate.javaclient.eventhandling.exceptionhandling.EventuateClientScheduler;
import io.eventuate.javaclient.jdbc.JdkTimerBasedEventuateClientScheduler;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Instance;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Optional;

@Singleton
public class EmbeddedTestAggregateStoreEventsConfiguration {

  @Singleton
  public EventuateDatabaseScriptSupplier idGenerator(@ConfigProperty(name = "eventuate.outbox.id") Optional<Long> id) {
    return () -> id
            .map(_id -> Collections.singletonList("eventuate-embedded-schema-db-id.sql"))
            .orElse(Collections.singletonList("eventuate-embedded-schema.sql"));
  }

  @Singleton
  public AggregateEvents aggregateEvents(Instance<io.eventuate.javaclient.commonimpl.events.sync.AggregateEvents> aggregateEvents) {
    return new SyncToAsyncAggregateEventsAdapter(aggregateEvents.get());
  }

  @Singleton
  public EventuateClientScheduler eventHandlerRecoveryScheduler() {
    return new JdkTimerBasedEventuateClientScheduler();
  }
}
