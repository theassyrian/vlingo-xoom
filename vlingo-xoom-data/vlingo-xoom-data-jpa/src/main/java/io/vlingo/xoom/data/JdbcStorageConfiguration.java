package io.vlingo.xoom.data;

import io.micronaut.jdbc.BasicJdbcConfiguration;
import io.vlingo.symbio.store.common.jdbc.Configuration;
import io.vlingo.symbio.store.common.jdbc.DatabaseType;
import io.vlingo.xoom.data.config.DataConfiguration;

import java.util.UUID;

public class JdbcStorageConfiguration extends Configuration {
    private final BasicJdbcConfiguration jdbcConfiguration;

    public JdbcStorageConfiguration(DatabaseType databaseType, DataConfiguration dataConfiguration,
                                    BasicJdbcConfiguration jdbcConfiguration) throws Exception {
        super(databaseType, interestOf(databaseType), jdbcConfiguration.getDriverClassName(),
                dataConfiguration.getDataFormat(), jdbcConfiguration.getUrl(), "",
                jdbcConfiguration.getUsername(), jdbcConfiguration.getPassword(),
                dataConfiguration.isUseSSL(), UUID.randomUUID().toString(), dataConfiguration.isCreateTables());
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public BasicJdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }
}
