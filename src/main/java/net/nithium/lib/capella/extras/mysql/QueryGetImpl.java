package net.nithium.lib.capella.extras.mysql;

import lombok.NonNull;
import lombok.SneakyThrows;
import net.minestom.server.thread.Acquirable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryGetImpl<T> implements QueryGet<T> {
    @NonNull private Connection connection;
    @NonNull private String query;
    @NonNull private SelectCall selectCall;
    @NonNull private List<Object> params;

    private static ResultSet resultSet;

    public QueryGetImpl(Connection connection, String query, List<Object> params) {
        this.connection = connection;
        this.query = query;
        this.params = params;

        doTask();
    }

    @SneakyThrows(SQLException.class)
    private void doTask() {
        Acquirable<PreparedStatement> statementAcquirable = Acquirable.of(connection.prepareStatement(query));

        statementAcquirable.async(preparedStatement -> {
            try {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject((i + 1), params.get(i));
                }

                resultSet = preparedStatement.executeQuery();;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @SneakyThrows
    @Override
    public T get(SelectCall selectCall) {
        this.selectCall = selectCall;

        return (T) selectCall.get(resultSet);
    }
}
