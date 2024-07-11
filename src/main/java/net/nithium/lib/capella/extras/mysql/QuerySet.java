package net.nithium.lib.capella.extras.mysql;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.minestom.server.thread.Acquirable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QuerySet {
    @NonNull private Connection connection;
    @NonNull private String query;
    @NonNull private List<Object> params;

    private static boolean done;

    public QuerySet(@NonNull Connection connection, @NonNull String query, @NonNull List<Object> params) {
        this.connection = connection;
        this.query = query;
        this.params = params;

        doTask();
    }

    @SneakyThrows(SQLException.class)
    private void doTask() {
        Acquirable<PreparedStatement> preparedStatementAcquirable = Acquirable.of(connection.prepareStatement(query));
        preparedStatementAcquirable.async(preparedStatement -> {
            try {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
                preparedStatement.execute();
                done = true;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isDone() {
        return done;
    }
}
