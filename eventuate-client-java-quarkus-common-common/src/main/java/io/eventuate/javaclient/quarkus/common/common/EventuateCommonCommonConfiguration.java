package io.eventuate.javaclient.quarkus.common.common;

import io.eventuate.javaclient.commonimpl.common.schema.ConfigurableEventSchema;
import io.eventuate.javaclient.commonimpl.common.schema.DefaultEventuateEventSchemaManager;
import io.eventuate.javaclient.commonimpl.common.schema.EventSchemaConfigurer;
import io.eventuate.javaclient.commonimpl.common.schema.EventuateEventSchemaManager;

import javax.enterprise.inject.Instance;
import javax.inject.Singleton;

@Singleton
public class EventuateCommonCommonConfiguration {

  @Singleton
  public EventuateEventSchemaManager eventSchemaMetadataManager(Instance<EventSchemaConfigurer> metadataManagerConfigurers) {
    DefaultEventuateEventSchemaManager eventSchemaManager = new DefaultEventuateEventSchemaManager();
    ConfigurableEventSchema configuration = new ConfigurableEventSchema(eventSchemaManager);
    metadataManagerConfigurers.stream().forEach(c -> c.configure(configuration));
    return eventSchemaManager;
  }
}
