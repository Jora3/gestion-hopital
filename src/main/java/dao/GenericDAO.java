package dao;

import annotations.Column;
import annotations.NotColumn;
import annotations.Table;
import modele.BaseModele;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.Utilitaire;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("All")
public class GenericDAO implements InterfaceDAO {

    private String pagination(int nPage, int nDonne) {
        int offSet = (nPage - 1) * nDonne + 1;
        return String.format("limit %d offset %d", nDonne, offSet);
    }

    private String nomTable(BaseModele modele) throws Exception {
        Table table = modele.getClass().getAnnotation(Table.class);
        if(table == null){
            if(modele.getTable() == null)
                throw new Exception(String.format("Objet de type : %s n'est pas associé à une table", modele.getClass()));
            return modele.getTable();
        }
        return table.name();
    }

    private String where(BaseModele modele, boolean isAnd) throws IllegalAccessException {
        StringBuilder request  = new StringBuilder();
        String        condition = "or ";
        if(isAnd) condition = "and ";

        Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
        for(Field field : fields){
            Column column = field.getAnnotation(Column.class);
            field.setAccessible(true);
            if(field.get(modele) != null)
                if(column != null) request.append(column.name()).append(" = ? ").append(condition);
                else request.append(field.getName()).append(" = ? ").append(condition);
        }
        int taille = request.length();
        if(5 < taille){
            request.delete(taille - 5, taille - 1);
            request = new StringBuilder(" where "+request);
        }
        return request.toString();
    }

    private Field[] columnsTable(Field[] fields) {
        List<Field> listColumns = new ArrayList<>();
        for (Field field : fields) {
            if(field.getAnnotation(NotColumn.class) == null) listColumns.add(field);
        }
        return listColumns.toArray(new Field[0]);
    }

    private String getColumns(BaseModele modele) {
        Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
        StringBuilder columns = new StringBuilder();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null)
                columns.append(column.name()).append(",");
            else
                columns.append(field.getName()).append(",");
        }
        return columns.substring(0, columns.length()-1);
    }

    private String getParams(BaseModele modele) {
        int nbColumns = columnsTable(modele.getClass().getDeclaredFields()).length;
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < nbColumns; i++)
            params.append("?,");
        return params.substring(0, params.length()-1);
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
    public List<BaseModele> findAll(BaseModele modele, boolean strict, int page, int nbDonne) throws Exception {
        ResultSet rs = null;
        String q = getRequeteFindAll(modele), aWhere = where(modele, strict), pagination = pagination(page, nbDonne);
        q += aWhere + pagination;
        try (Connection conn = UtilDAO.getConnection();
             PreparedStatement statement = conn.prepareStatement(q)) {
            if(!aWhere.equals("")){
                Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
                int     i      = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object o = field.get(modele);
                    if(o != null){
                        statement.setObject(i, o);
                        i++;
                    }
                }
            }
            return list(modele, rs = statement.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            UtilDAO.closeRessources(rs, null, null);
        }
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict) throws Exception {
        return findAll(modele, strict, 1, 20);
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele) throws Exception {
        return findAll(modele, true, 1, 20);
    }

    @Override
    public List<BaseModele> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public void findById(BaseModele modele) throws Exception {
        ResultSet rs = null;
        try (Connection conn = UtilDAO.getConnection();
             PreparedStatement statement = conn.prepareStatement(getRequeteFindById(modele))) {
            statement.setObject(1, modele.getId());
            rs = statement.executeQuery();
            List<BaseModele> list = list(modele, rs);
            if(list.size() != 0) modele = list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            UtilDAO.closeRessources(rs, null, null);
        }
    }

    @Override
    public void delete(BaseModele modele) throws Exception {
        try(Connection conn = UtilDAO.getConnection();
        PreparedStatement statement= conn.prepareStatement(getRequeteDelete(modele))){
            statement.setObject(1,modele.getId());
            statement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
     public void update(BaseModele modele) throws Exception {
         try(Connection c = UtilDAO.getConnection();
            PreparedStatement st = c.prepareStatement(getRequeteUpdate(modele))){
            int i = 1;
            Field[]   fields  = columnsTable(modele.getClass().getDeclaredFields());
            for(Field field : fields){
                field.setAccessible(true);
                st.setObject(i, field.get(modele));
                i++;
            }
            st.setObject(i, modele.getId());
            System.out.print(getRequeteUpdate(modele)); 
            st.executeUpdate();
        }

    }

    @Override
    public void save(BaseModele modele) throws Exception {
        try (
            Connection connection = UtilDAO.getConnection();
            PreparedStatement statement = connection.prepareStatement(getRequeteSave(modele))
            ) {
            setParamsSave(statement, modele);
            statement.executeUpdate();
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    private void setParamsSave(PreparedStatement statement, BaseModele modele) throws Exception {
        Class classe = modele.getClass();
        Field[] fields = columnsTable(classe.getDeclaredFields());
        int i = 1;
        Method method;
        for (Field field : fields) {
            method = classe.getMethod("get" + Utilitaire.capitalize(field.getName()));
            statement.setObject(i, method.invoke(modele));
            i++;
        }
    }

    private String getRequeteSave(BaseModele modele) throws Exception {
        String sql = "INSERT INTO %s (%s) VALUES(%s)";
        return String.format(sql, nomTable(modele), getColumns(modele), getParams(modele));
    }

    @Override
    public String getRequeteFindAll() {
        return null;
    }

    @Override
    public String getRequeteFindAll(BaseModele modele) throws Exception {
        return String.format("select * from %s", nomTable(modele));
    }

    @Override
    public String getRequeteFindById(BaseModele modele) throws Exception {
        return String.format("select * from %s where id = ?", nomTable(modele));
    }

    private String getRequeteUpdate(BaseModele modele) throws Exception {
        Field[]       fields = columnsTable(modele.getClass().getDeclaredFields());
        StringBuilder values = new StringBuilder();
        for(Field f : fields){
            values.append(",").append(f.getName()).append(" = ?");
        }
          values = new StringBuilder(values.toString().replaceFirst(",", ""));
        return String.format("update %s set %s where id = ?", nomTable(modele), values.toString());
    }

    @Override
    public String getRequeteDelete(BaseModele modele) throws Exception {
        return String.format("delete from %s where id= ?", nomTable(modele));
    }
}
