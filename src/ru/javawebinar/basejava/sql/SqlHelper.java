package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private static final String VIOLATION_UNIQUE_CONSTRAINT = "23505";
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public interface BlockOfCode<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public <T> T process(String sql, BlockOfCode<T> blockOfCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            T retValue = blockOfCode.execute(ps);

            return retValue;

        } catch (SQLException e) {
            if (e.getSQLState().equals(VIOLATION_UNIQUE_CONSTRAINT)) {
                throw new ExistStorageException(sql);
            }
            throw new StorageException(e);
        }
    }


}
