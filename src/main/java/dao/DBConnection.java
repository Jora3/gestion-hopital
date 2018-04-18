package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private String jdbc;
    private String hostname;
    private int port;
    private String dbname;
    private String user;
    private String pass;

    public Connection getConnection() throws Exception {
        Class.forName(getDriverName());
        return DriverManager.getConnection(getUrl(), user, pass);
    }

    private String getUrl() {
        return "jdbc:" + jdbc + "://" + hostname + ":" + port + "/" + dbname;
    }

    private String getDriverName() throws Exception {
        switch (jdbc) {
            case "mysql":
                return "com.mysql.jdbc.Driver";
            case "postgresql":
                return "org.postgresql.Driver";
            default:
                throw new Exception("Driver introuvable. Cause : JDBC invalide : " + jdbc);
        }
    }

    public DBConnection(String jdbc, String hostname, int port, String dbname, String user, String pass) throws Exception {
        this.setJdbc(jdbc);
        this.setHostname(hostname);
        this.setPort(port);
        this.setDbname(dbname);
        this.setUser(user);
        this.setPass(pass);
    }

    public DBConnection() { }

    public String getJdbc() {
        return jdbc;
    }

    private void setJdbc(String jdbc) throws Exception {
        if (jdbc.isEmpty()) {
            throw new Exception("JDBC vide");
        } else {
            this.jdbc = jdbc;
        }
    }

    public String getHostname() {
        return hostname;
    }

    private void setHostname(String hostname) throws Exception {
        if (hostname.isEmpty()) {
            throw new Exception("Nom d'hote vide");
        } else {
            this.hostname = hostname;
        }
    }

    public int getPort() {
        return port;
    }

    private void setPort(int port) throws Exception {
        if (port > 0)
            this.port = port;
        else
            throw new Exception("Port négatif : " + port);
    }

    public String getDbname() {
        return dbname;
    }

    private void setDbname(String dbname) throws Exception {
        if (dbname.isEmpty()) {
            throw new Exception("Nom de la base de donnée vide");
        } else {
            this.dbname = dbname;
        }
    }

    public String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    private void setPass(String pass) {
        this.pass = pass;
    }
}
