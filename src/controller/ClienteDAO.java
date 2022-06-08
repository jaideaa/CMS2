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
import model.Cliente;

/**
 *
 * @author jaide
 */
public class ClienteDAO {
    
    public static boolean insert(Cliente c) {
        boolean b = false;
        String sql = "INSERT INTO clientes (id, nome, endereco, telefone) "
                + "VALUES (?, ?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = Conexao.get();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, c.getId());
            ps.setString(2, c.getNome());
            ps.setString(3, c.getEndereco());
            ps.setString(4, c.getTelefone());
            
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
    
    public static boolean update(Cliente c) {
        boolean b = false;
        return b;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static boolean delete(String id) {
        boolean b = false;
        return b;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static List<Cliente> list() {
        List<Cliente> lista = new ArrayList<>();
        Connection conn;
        String sql = "SELECT * FROM Clientes";
        
        try {
            conn = Conexao.get();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getString("id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setTelefone(rs.getString("telefone"));
                lista.add(c);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return lista;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static Cliente getById(String id) {
        Cliente c = new Cliente();
        Connection conn;
        String sql = "SELECT * FROM Clientes WHERE id = ?";
        
        try {
            conn = Conexao.get();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                c = new Cliente();
                c.setId(rs.getString("id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setTelefone(rs.getString("telefone"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return c;
    }
}
