package ru.kpfu.itis.Kolodeznikova.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {

    private final LinkedList<Connection> pool = new LinkedList<>();
    private final String url;
    private final String user;
    private final String password;

    public ConnectionPool(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        initializePool();
    }

    private void initializePool() throws SQLException {
        int POOL_SIZE = 10;
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection());
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public synchronized Connection getConnection() throws SQLException {
        if (pool.isEmpty()) {
            return createConnection();
        } else {
            return pool.removeFirst();
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            pool.addLast(connection);
        }
    }

    public synchronized void closePool() throws SQLException {
        for (Connection connection : pool) {
            connection.close();
        }
        pool.clear();
    }

}
