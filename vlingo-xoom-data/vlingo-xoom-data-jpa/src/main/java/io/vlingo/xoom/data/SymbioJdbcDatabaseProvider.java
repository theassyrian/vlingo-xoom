package io.vlingo.xoom.data;

import io.micronaut.context.annotation.Requires;
import io.micronaut.jdbc.BasicJdbcConfiguration;
import io.vlingo.symbio.store.common.jdbc.DatabaseType;
import io.vlingo.xoom.data.config.DataConfiguration;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
@Requires(beans = DataSource.class)
public class SymbioJdbcDatabaseProvider {

    private final DataConfiguration dataConfiguration;
    private final DataSource dataSource;
    private final JdbcStorageConfiguration jdbcStorageConfiguration;

    public SymbioJdbcDatabaseProvider(DataConfiguration dataConfiguration, DataSource dataSource,
                                      BasicJdbcConfiguration jdbcConfiguration)
            throws Exception {
        this.dataConfiguration = dataConfiguration;
        this.dataSource = dataSource;
        this.jdbcStorageConfiguration = new JdbcStorageConfiguration(dataConfiguration.getDatabaseType() == null ?
                DatabaseType.databaseType(jdbcConfiguration.getUrl()) :
                DatabaseType.databaseType(dataConfiguration.getDatabaseType()), dataConfiguration, jdbcConfiguration);
    }

    public DataConfiguration getDataConfiguration() {
        return dataConfiguration;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcStorageConfiguration getJdbcStorageConfiguration() {
        return jdbcStorageConfiguration;
    }
}
