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
import modele.Mouvement;

/**
 *
 * @author Nathalie Andrandrain
 */
public class MouvementDAO {
     public static void save(Mouvement b) throws Exception {
        Connection conn = null; 
        PreparedStatement ps = null;
        String sql = "INSERT INTO mouvemet(maladie, service, chambre, dateEntre, dateSortie) VALUES (?, ?, ?, ?, ?)";
        try {
           conn = UtilDAO.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, b.getPatient());
            ps.setInt(2, b.getService());
            ps.setInt(3, b.getChambre());
            ps.setDate(4, b.getDateEntre());
            ps.setDate(5, b.getDateSortie());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            UtilDAO.closeRessources(null, ps, conn);
        }
    }
     public static List<Mouvement> findAll() throws Exception {
        List<Mouvement> rep = new ArrayList<Mouvement>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT * FROM mouvement";

        try {
            conn = UtilDAO.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Mouvement com = new Mouvement();
                com.setId(rs.getInt(1));
                com.setPatient(rs.getInt(2));
                com.setService(rs.getInt(3));
                com.setChambre(rs.getInt(4));
                com.setDateEntre(rs.getDate(5));
                com.setDateSortie(rs.getDate(6));
                rep.add(com);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            UtilDAO.closeRessources(rs, ps, conn);
        }
        return rep;
    } 
      public void update(Mouvement m) throws Exception {
       Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update mouvement set patient = ?, service = ?, chambre = ?, dateEntre = ?, dateSortie = ? where id = ? ";
        try {
            conn = UtilDAO.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, m.getPatient());
            ps.setInt(2, m.getService());
            ps.setInt(3, m.getChambre());
            ps.setDate(4, m.getDateEntre());
            ps.setDate(5, m.getDateSortie());
             ps.setInt(6, m.getId());
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
        String sql = "delete from mouvement where id = ? ";
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
