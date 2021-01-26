package io.eventuate.javaclient.quarkus.jdbc;

import io.eventuate.common.jdbc.EventuateJdbcStatementExecutor;
import io.eventuate.common.jdbc.EventuateSchema;
import io.eventuate.javaclient.jdbc.EventuateJdbcAccess;
import io.eventuate.javaclient.jdbc.common.tests.CommonEventuateJdbcAccessImplTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class EventuateJdbcAccessImplTest extends CommonEventuateJdbcAccessImplTest {

  @Inject
  EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor;

  @Inject
  EventuateJdbcAccess eventuateJdbcAccess;

  @Inject
  EventuateSchema eventuateSchema;

  @Override
  protected EventuateSchema getEventuateSchema() {
    return eventuateSchema;
  }

  @Override
  protected String readAllEventsSql() {
    return "select * from eventuate.events";
  }

  @Override
  protected String readAllEntitiesSql() {
    return "select * from eventuate.entities";
  }

  @Override
  protected String readAllSnapshots() {
    return "select * from eventuate.snapshots";
  }

  @BeforeEach
  public void init() {
    clear();
  }

  @Test
  @Override
  public void testSave() {
    super.testSave();
  }

  @Test
  @Override
  public void testFind() {
    super.testFind();
  }

  @Test
  @Override
  public void testUpdate() {
    super.testUpdate();
  }

  @Override
  protected EventuateJdbcStatementExecutor getEventuateJdbcStatementExecutor() {
    return eventuateJdbcStatementExecutor;
  }

  @Override
  protected EventuateJdbcAccess getEventuateJdbcAccess() {
    return eventuateJdbcAccess;
  }
}
