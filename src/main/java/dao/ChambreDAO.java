/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modele.Chambre;

/**
 *
 * @author Nathalie Andrandrain
 */
public class ChambreDAO {
    public static void save(Chambre b) throws Exception {
        Connection conn = null; 
        PreparedStatement ps = null;
        String sql = "INSERT INTO chambre(numero, disponible) VALUES (?, ?)";
        try {
           conn = UtilDAO.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, b.getNumero());
            ps.setInt(2, b.getDisponible());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            UtilDAO.closeRessources(null, ps, conn);
        }
    }
     public static List<Chambre> findAll() throws Exception {
        List<Chambre> rep = new ArrayList<Chambre>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT * FROM chambre";

        try {
            conn = UtilDAO.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Chambre com = new Chambre();
                com.setId(rs.getInt(1));
                com.setNumero(rs.getString(2));
                com.setDisponible(rs.getInt(3));
                rep.add(com);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            UtilDAO.closeRessources(rs, ps, conn);
        }
        return rep;
    }
      public void update(Chambre m) throws Exception {
       Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update chambre set numero = ?, disponible = ? where id = ? ";
        try {
            conn = UtilDAO.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNumero());
            ps.setInt(2, m.getDisponible());
             ps.setInt(3, m.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
          UtilDAO.closeRessources(null, ps, conn);
        } 
    
    }
       public void delete(int id) throws Exception {
       Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from chambre where id = ? ";
        try {
            conn = UtilDAO.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
          UtilDAO.closeRessources(null, ps, conn);
        } 
    
    }
    
}
