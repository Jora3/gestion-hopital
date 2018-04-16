package dao;

import utils.Configuration;

import java.sql.Connection;

class UtilDAO {
    static Connection getConnection() throws Exception {
        return getConnection(Configuration.jdbc, Configuration.hostname, Configuration.port, Configuration.dbname, Configuration.user, Configuration.pass);
    }

    private static Connection getConnection(String jdbc, String hostname, int port, String dbname, String user, String pass) throws Exception {
        DBConnection dbConnection = new DBConnection(jdbc, hostname, port, dbname, user, pass);
        return dbConnection.getConnection();
    }
}
