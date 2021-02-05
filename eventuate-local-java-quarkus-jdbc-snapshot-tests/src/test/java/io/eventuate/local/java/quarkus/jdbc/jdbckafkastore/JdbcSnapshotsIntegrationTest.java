package io.eventuate.local.java.quarkus.jdbc.jdbckafkastore;

import io.eventuate.common.quarkus.jdbc.test.configuration.TestProfileResolver;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(TestProfileResolver.class)
public class JdbcSnapshotsIntegrationTest extends AbstractSnapshotsIntegrationTest {

}
