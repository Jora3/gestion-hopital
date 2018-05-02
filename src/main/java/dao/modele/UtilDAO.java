package dao.modele;

import dao.connection.DBConnection;
import utils.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UtilDAO {
    static void closeRessources(ResultSet resultSet, PreparedStatement statement, Connection connection) throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }

    static Connection getConnection() throws Exception {
        return getConnection(Configuration.jdbc, Configuration.hostname, Configuration.port, Configuration.dbname, Configuration.user, Configuration.pass);
    }

    private static Connection getConnection(String jdbc, String hostname, int port, String dbname, String user, String pass) throws Exception {
        DBConnection dbConnection = new DBConnection(jdbc, hostname, port, dbname, user, pass);
        return dbConnection.getConnection();
    }
}
