package io.vlingo.xoom.data;

import io.micronaut.context.annotation.Requires;
import io.micronaut.jdbc.BasicJdbcConfiguration;
import io.vlingo.symbio.StateAdapterProvider;
import io.vlingo.symbio.store.object.jdbc.*;
import io.vlingo.symbio.store.object.jdbc.jpa.JPAObjectStore;
import io.vlingo.symbio.store.object.jdbc.jpa.JPAObjectStoreActor;
import io.vlingo.symbio.store.object.jdbc.jpa.JPAObjectStoreDelegate;
import io.vlingo.xoom.server.VlingoScene;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
@Requires(beans = DataSource.class)
public class JpaObjectStoreProvider {

    private JPAObjectStoreDelegate delegate;
    private BasicDispatcher storageDispatcher;
    private JPAObjectStore objectStore;
    private JDBCObjectStoreEntryJournalQueries queries;
    private VlingoScene scene;
    private final SymbioJdbcDatabaseProvider databaseProvider;

    public JpaObjectStoreProvider(VlingoScene scene, SymbioJdbcDatabaseProvider databaseProvider) {
        this.scene = scene;
        this.databaseProvider = databaseProvider;
        configure();
    }

    private void configure() {
        storageDispatcher = new BasicDispatcher();
        String persistenceUnit = getPersistenceUnitName();

        delegate = new JPAObjectStoreDelegate(persistenceUnit, getDefaultDatabaseProperties(),
                UUID.randomUUID().toString(),
                StateAdapterProvider.instance(scene.getWorld()),
                scene.getWorld().defaultLogger());

        queries = getCreateQueries(databaseProvider.getJdbcStorageConfiguration().connectionProvider.connection());

        objectStore = scene.getWorld().actorFor(JPAObjectStore.class, JPAObjectStoreActor.class, delegate,
                databaseProvider.getJdbcStorageConfiguration().connectionProvider, storageDispatcher);
    }

    private JDBCObjectStoreEntryJournalQueries getCreateQueries(Connection connection) {
        JDBCObjectStoreEntryJournalQueries queries;

        switch (databaseProvider.getJdbcStorageConfiguration().databaseType) {
            case HSQLDB:
                queries = new HSQLDBObjectStoreEntryJournalQueries(connection);
                break;
            case MySQL:
                queries = new MySQLObjectStoreEntryJournalQueries(connection);
                break;
            case Postgres:
                queries = new PostgresObjectStoreEntryJournalQueries(connection);
                break;
            case YugaByte:
                queries = new YugaByteObjectStoreEntryJournalQueries(connection);
                break;
            default:
                throw new IllegalArgumentException("Database currently not supported: " + databaseProvider
                        .getJdbcStorageConfiguration().databaseType.name());
        }

        return queries;
    }

    private String getPersistenceUnitName() {
        String persistenceUnit;
        switch (databaseProvider.getJdbcStorageConfiguration().databaseType) {
            case HSQLDB:
                persistenceUnit = JPAObjectStoreDelegate.JPA_HSQLDB_PERSISTENCE_UNIT;
                break;
            case MySQL:
                persistenceUnit = JPAObjectStoreDelegate.JPA_MYSQL_PERSISTENCE_UNIT;
                break;
            case Postgres:
                persistenceUnit = JPAObjectStoreDelegate.JPA_POSTGRES_PERSISTENCE_UNIT;
                break;
            case YugaByte:
                persistenceUnit = JPAObjectStoreDelegate.JPA_YUGABYTE_PERSISTENCE_UNIT;
                break;
            default:
                persistenceUnit = null;
                scene.getWorld().defaultLogger()
                        .error("The database type for this Vlingo application is not unsupported for " +
                                "an auto-configured JPAObjectStoreDelegate.");
        }

        return persistenceUnit;
    }

    protected Map<String, Object> getDefaultDatabaseProperties() {
        final Map<String, Object> properties = new HashMap<>();
        BasicJdbcConfiguration configuration = databaseProvider.getJdbcStorageConfiguration()
                .getJdbcConfiguration();

        properties.put("javax.persistence.jdbc.driver", configuration.getDriverClassName());
        properties.put("javax.persistence.jdbc.url", configuration.getUrl());
        properties.put("javax.persistence.jdbc.user", configuration.getUsername());
        properties.put("javax.persistence.jdbc.password", configuration.getPassword());
        properties.put("javax.persistence.LockModeType", "OPTIMISTIC_FORCE_INCREMENT");
        properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.put("javax.persistence.schema-generation.create-database-schemas", "drop-and-create");
        properties.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");
        properties.put("javax.persistence.schema-generation.scripts.create-target", "./target/createDDL.jdbc");
        properties.put("javax.persistence.schema-generation.scripts.drop-target", "./target/dropDDL.jdbc");
        properties.put("javax.persistence.schema-generation.create-source", "metadata");
        properties.put("javax.persistence.schema-generation.drop-source", "metadata");
        properties.put("javax.persistence.schema-generation.create-script-source", "./target/createDDL.jdbc");
        properties.put("javax.persistence.schema-generation.drop-script-source", "./target/dropDDL.jdbc");

        return properties;
    }

    public JPAObjectStoreDelegate getDelegate() {
        return delegate;
    }

    public BasicDispatcher getStorageDispatcher() {
        return storageDispatcher;
    }

    public JPAObjectStore getObjectStore() {
        return objectStore;
    }

    public JDBCObjectStoreEntryJournalQueries getQueries() {
        return queries;
    }

    public VlingoScene getScene() {
        return scene;
    }

    public SymbioJdbcDatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }
}
