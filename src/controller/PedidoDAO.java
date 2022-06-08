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
import model.Pedido;
import model.PedidoDetalhe;

/**
 *
 * @author jaide
 */
public class PedidoDAO {
    
    public static boolean insert(Pedido p, PedidoDetalhe d) {
        boolean b = false;
        String sql = "INSERT INTO pedidos (numero, idCliente, dataPedido) "
                + "VALUES (?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = Conexao.get();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, p.getNumero());
            ps.setString(2, p.getIdCliente());
            ps.setDate(3, p.getDataPedido());
            
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
        
        sql = "INSERT INTO pedidosDetalhes (numeroPedido, idVeiculo) "
                + "VALUES (?, ?)";
        
        try {
            if (conn == null) { conn = Conexao.get(); }
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, d.getNumeroPedido());
            ps.setString(2, d.getIdVeiculo());
            
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
    
    public static boolean update(Pedido p, PedidoDetalhe d) {
        boolean b = false;
        return b;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static boolean delete(String id) {
        boolean b = false;
        return b;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static List<Pedido> listaPedidos() {
        List<Pedido> listaP = new ArrayList<>();
        
        Connection conn;
        String sql = "SELECT * FROM pedidos";
        
        try {
            conn = Conexao.get();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setNumero(rs.getString("numero"));
                p.setIdCliente(rs.getString("idCliente"));
                p.setDataPedido(rs.getDate("dataPedido"));
                listaP.add(p);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaP;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static List<PedidoDetalhe> listaDetalhes() {
        List<PedidoDetalhe> listaD = new ArrayList<>();
        
        Connection conn;
        String sql = "SELECT * FROM pedidosDetalhes";
        
        try {
            conn = Conexao.get();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                PedidoDetalhe d = new PedidoDetalhe();
                d.setNumeroPedido(rs.getString("numeroPedido"));
                d.setIdVeiculo(rs.getString("idVeiculo"));
                listaD.add(d);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return listaD;
    }
    
    /* -------------------------------------------------------------------- */
    
    public static PedidoDetalhe getDetalheById(String id) {
        PedidoDetalhe d = new PedidoDetalhe();
        Connection conn;
        String sql = "SELECT * FROM pedidosDetalhes WHERE numeroPedido = ?";
        
        try {
            conn = Conexao.get();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                d = new PedidoDetalhe();
                d.setNumeroPedido(rs.getString("numeroPedido"));
                d.setIdVeiculo(rs.getString("idVeiculo"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return d;
    }
}
