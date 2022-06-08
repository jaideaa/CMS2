/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Veiculo;

/**
 *
 * @author jaide
 */
public class VeiculoDAO {
    
    public static boolean insert(Veiculo v) {
        boolean b = false;
        String sql = "INSERT INTO veiculos (id, nome, fabricante, ano, cor, motorizacao, quantidade, valor) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = Conexao.get();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, v.getId());
            ps.setString(2, v.getNome());
            ps.setString(3, v.getFabricante());
            ps.setInt(4, v.getAno());
            ps.setString(5, v.getCor());
            ps.setString(6, v.getMotorizacao());
            ps.setInt(7, v.getQuantidade());
            ps.setInt(8, v.getValor());
            
            int affectedRecords = ps.executeUpdate();
            if (affectedRecords > 0) { b = true; }
        } catch(SQLException e) {
            e.getMessage();
            try {
                if (conn != null) { conn.close(); }
            } catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return b;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static boolean update(Veiculo v) {
        boolean b = false;
        
        return b;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static boolean delete(String id) {
        boolean b = false;
        return b;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static List<Veiculo> list() {
        List<Veiculo> lista = new ArrayList<>();
        Connection conn;
        String sql = "SELECT * FROM veiculos";
        
        try {
            conn = Conexao.get();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getString("id"));
                v.setNome(rs.getString("nome"));
                v.setFabricante(rs.getString("fabricante"));
                v.setAno(rs.getInt("ano"));
                v.setCor(rs.getString("cor"));
                v.setMotorizacao(rs.getString("motorizacao"));
                v.setQuantidade(rs.getInt("quantidade"));
                v.setValor(rs.getInt("valor"));
                lista.add(v);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return lista;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static Veiculo getById(String id) {
        Connection conn;
        String sql = "SELECT * FROM veiculos WHERE id = ?";
        Veiculo v = new Veiculo();
        
        try {
            conn = Conexao.get();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                v.setId(rs.getString("id"));
                v.setNome(rs.getString("nome"));
                v.setFabricante(rs.getString("fabricante"));
                v.setAno(rs.getInt("ano"));
                v.setCor(rs.getString("cor"));
                v.setMotorizacao(rs.getString("motorizacao"));
                v.setQuantidade(rs.getInt("quantidade"));
                v.setValor(rs.getInt("valor"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return v;
    }
}
