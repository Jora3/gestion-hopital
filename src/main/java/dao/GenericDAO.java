package dao;

import annotations.Column;
import annotations.NotColumn;
import annotations.Table;
import modele.BaseModele;
import utils.Configuration;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO implements InterfaceDAO {

    private Field[] columnsTable(Field[] fields) {
        List<Field> listColumns = new ArrayList<>();
        for (Field field : fields) {
            if(field.getAnnotation(NotColumn.class) == null) listColumns.add(field);
        }
        return listColumns.toArray(new Field[0]);
    }

    private List<BaseModele> list(BaseModele modele, ResultSet rs) throws Exception {
        List<BaseModele> modeleList = new ArrayList<>();
        try {
            Class classes = modele.getClass();
            Field[]   fields  = columnsTable(classes.getDeclaredFields());
            while (rs.next()){
                modele = modele.getClass().newInstance();
                modele.setId(rs.getInt("id"));
                for(Field f : fields){
                    f.setAccessible(true);
                    Column column = f.getAnnotation(Column.class);
                    if(column != null){ f.set(modele, rs.getObject(column.name()));}
                    else f.set(modele, rs.getObject(f.getName()));
                }
                modeleList.add(modele);
            }
            return modeleList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<BaseModele> findAll(){
        return null;
    }

    @Override
    public List<BaseModele> findAll(BaseModele baseModele) throws Exception {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(getRequeteFindAll(baseModele));
             ResultSet rs = statement.executeQuery()) {
             return list(baseModele, rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public BaseModele findById(BaseModele modele) throws Exception {
        ResultSet rs = null;
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(getRequeteFindById(modele))) {
            statement.setObject(1, modele.getId());
            rs = statement.executeQuery();
            List<BaseModele> list = list(modele, rs);
            if(list.size() != 0) return list.get(0);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            closeRessources(rs, null, null);
        }
    }

    @Override
    public void delete(BaseModele modele) throws Exception {

    }

    @Override
    public void update(BaseModele modele) throws Exception {

    }

    @Override
    public void save(BaseModele modele) throws Exception {

    }

    @Override
    public String getRequeteFindAll() {
        return null;
    }

    @Override
    public String getRequeteFindAll(BaseModele modele) {
        Table table = modele.getClass().getAnnotation(Table.class);
        return String.format("select * from %s", table.name());
    }

    @Override
    public String getRequeteFindById(BaseModele modele) {
        Table table = modele.getClass().getAnnotation(Table.class);
        return String.format("select * from %s where id = ?", table.name());
    }

    @Override
    public String getRequeteDelete(BaseModele modele) {
        return null;
    }

    @Override
    public void closeRessources(ResultSet resultSet, Statement statement, Connection connection) throws Exception {
        if(resultSet != null) resultSet.close();
        if(statement != null) statement.close();
        if(connection != null) connection.close();
    }

    @Override
    public Connection getConnection() throws Exception {
        return getConnection(Configuration.jdbc, Configuration.hostname, Configuration.port, Configuration.dbname, Configuration.user, Configuration.pass);
    }

    @Override
    public Connection getConnection(String jdbc, String hostname, int port, String dbname, String user, String pass) throws Exception {
        DBConnection dbConnection = new DBConnection(jdbc, hostname, port, dbname, user, pass);
        return dbConnection.getConnection();
    }
}
