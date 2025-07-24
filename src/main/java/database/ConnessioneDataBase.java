package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDataBase {
    private static ConnessioneDataBase instance;

    private static Connection connection;

    private static final String NOME = "postgres";
    private static final String PASSWORD = "pippo";
    private static final String URL = "jdbc:postgresql://localhost:5432/ToDodatabase";
    private static final String DRIVER = "org.postgresql.Driver";

    private ConnessioneDataBase() throws SQLException {
        connection = DriverManager.getConnection(URL, NOME, PASSWORD);
    }

    public static ConnessioneDataBase getInstance() throws SQLException {
        if (instance == null || connection.isClosed())
            instance = new ConnessioneDataBase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
