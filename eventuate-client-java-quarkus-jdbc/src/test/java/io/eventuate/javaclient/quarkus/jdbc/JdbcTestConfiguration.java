package io.eventuate.javaclient.quarkus.jdbc;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class JdbcTestConfiguration {
  @Singleton
  public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
