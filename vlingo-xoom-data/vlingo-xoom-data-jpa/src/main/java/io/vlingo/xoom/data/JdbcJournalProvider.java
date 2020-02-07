package io.vlingo.xoom.data;

import io.micronaut.context.annotation.Requires;
import io.vlingo.symbio.store.journal.Journal;
import io.vlingo.symbio.store.journal.jdbc.JDBCJournalActor;
import io.vlingo.xoom.server.VlingoScene;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
@Requires(beans = DataSource.class)
public class JdbcJournalProvider {
    private Journal<String> journal;
    private final VlingoScene scene;
    private final SymbioJdbcDatabaseProvider databaseProvider;

    public JdbcJournalProvider(VlingoScene scene, SymbioJdbcDatabaseProvider databaseProvider) {
        this.scene = scene;
        this.databaseProvider = databaseProvider;
        scene.start();
        journal = Journal.using(scene.getWorld().stage(), JDBCJournalActor.class, new BasicDispatcher(),
                databaseProvider.getJdbcStorageConfiguration());
    }

    public Journal<String> getJournal() {
        return journal;
    }

    public VlingoScene getScene() {
        return scene;
    }

    public SymbioJdbcDatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }
}
