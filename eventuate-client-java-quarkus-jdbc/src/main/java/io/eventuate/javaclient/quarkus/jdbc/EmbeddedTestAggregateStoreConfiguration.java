package io.eventuate.javaclient.quarkus.jdbc;

import io.eventuate.common.id.IdGenerator;
import io.eventuate.common.inmemorydatabase.EventuateDatabaseScriptSupplier;
import io.eventuate.common.jdbc.EventuateCommonJdbcOperations;
import io.eventuate.common.jdbc.EventuateJdbcStatementExecutor;
import io.eventuate.common.jdbc.EventuateSchema;
import io.eventuate.common.jdbc.EventuateTransactionTemplate;
import io.eventuate.common.jdbc.sqldialect.SqlDialectSelector;
import io.eventuate.javaclient.commonimpl.crud.AggregateCrud;
import io.eventuate.javaclient.commonimpl.crud.adapters.SyncToAsyncAggregateCrudAdapter;
import io.eventuate.javaclient.commonimpl.events.AggregateEvents;
import io.eventuate.javaclient.commonimpl.events.adapters.SyncToAsyncAggregateEventsAdapter;
import io.eventuate.javaclient.eventhandling.exceptionhandling.EventuateClientScheduler;
import io.eventuate.javaclient.jdbc.EventuateEmbeddedTestAggregateStore;
import io.eventuate.javaclient.jdbc.EventuateJdbcAccess;
import io.eventuate.javaclient.jdbc.EventuateJdbcAccessImpl;
import io.eventuate.javaclient.jdbc.JdkTimerBasedEventuateClientScheduler;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Instance;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Optional;

@Singleton
@Alternative
@Priority(0)
public class EmbeddedTestAggregateStoreConfiguration {

  @Singleton
  public EventuateJdbcAccess eventuateJdbcAccess(IdGenerator idGenerator,
                                                 EventuateTransactionTemplate eventuateTransactionTemplate,
                                                 EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
                                                 EventuateCommonJdbcOperations eventuateCommonJdbcOperations,
                                                 EventuateSchema eventuateSchema,
                                                 SqlDialectSelector sqlDialectSelector,
                                                 @ConfigProperty(name = "eventuateDatabase") String dbName) {

    return new EventuateJdbcAccessImpl(idGenerator,
            eventuateTransactionTemplate,
            eventuateJdbcStatementExecutor,
            eventuateCommonJdbcOperations,
            sqlDialectSelector.getDialect(dbName, Optional.empty()),
            eventuateSchema);
  }

  @Singleton
  public EventuateEmbeddedTestAggregateStore eventuateEmbeddedTestAggregateStore(EventuateJdbcAccess eventuateJdbcAccess) {
    return new EventuateEmbeddedTestAggregateStore(eventuateJdbcAccess);
  }

  @Singleton
  public AggregateCrud aggregateCrud(Instance<io.eventuate.javaclient.commonimpl.crud.sync.AggregateCrud> aggregateCrud) {
    return new SyncToAsyncAggregateCrudAdapter(aggregateCrud.get());
  }

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
