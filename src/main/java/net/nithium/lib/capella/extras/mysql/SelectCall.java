package net.nithium.lib.capella.extras.mysql;

import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SelectCall<T> {

    T get(@NonNull ResultSet resultSet) throws SQLException;
}
