package io.vlingo.xoom.data.config;

import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Primary;
import io.vlingo.symbio.store.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link DataConfiguration} class loads application properties from the application.yml file on the
 * classpath of the application.
 */
@ConfigurationProperties(DataConfiguration.PREFIX)
@Primary
@BootstrapContextCompatible
@Context
public class DataConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataConfiguration.class);
    public static final String PREFIX = "vlingo.symbio";

    private boolean useSSL = false;
    private String databaseType;
    private boolean createTables = false;
    private DataFormat dataFormat = DataFormat.Text;

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public boolean isCreateTables() {
        return createTables;
    }

    public void setCreateTables(boolean createTables) {
        this.createTables = createTables;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }
}
