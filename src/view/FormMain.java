/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author jaide
 */
public class FormMain extends JFrame {
    
    private JMenuBar barraMenu;
    private BorderLayout layout;
        
    public FormMain() {
        init();
        setVisible(true);
    }
    
    /* -------------------------------------------------------------------- */
    
    private void init() {
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        layout = new BorderLayout();
        setLayout(layout);
        
        barraMenu = criarPainelMenu();
        setJMenuBar(barraMenu);
        
    }
    
    /* -------------------------------------------------------------------- */
    
    private JMenuBar criarPainelMenu() {
        JMenuBar mb = new JMenuBar();
        
        JMenu menuSistema = new JMenu("Sistema");
        JMenu menuCadastros = new JMenu("Cadastros");
        
        JMenuItem menuItemSistema_Sair = new JMenuItem("Sair");
        menuItemSistema_Sair.addActionListener(new menuSair_OnClick());
        
        JMenuItem menuItemCad_Clientes = new JMenuItem("Clientes");
        menuItemCad_Clientes.addActionListener(new menuClientes_OnClick());
        
        JMenuItem menuItemCad_Veiculos = new JMenuItem("Veículos");
        menuItemCad_Veiculos.addActionListener(new menuVeiculos_OnClick());
        
        JMenuItem menuItemCad_Pedidos = new JMenuItem("Pedidos");
        menuItemCad_Pedidos.addActionListener(new menuPedidos_OnClick());
        
        JMenuItem menuItemCad_Vendas = new JMenuItem("Vendas Realizadas");
        menuItemCad_Vendas.addActionListener(new menuVendas_OnClick());
        
        menuSistema.add(menuItemSistema_Sair);
        menuCadastros.add(menuItemCad_Clientes);
        menuCadastros.add(menuItemCad_Veiculos);
        menuCadastros.add(menuItemCad_Pedidos);
        menuCadastros.add(menuItemCad_Vendas);
        
        mb.add(menuSistema);
        mb.add(menuCadastros);
        
        return mb;
    }
    
    /* -------------------------------------------------------------------- */
    
    private class menuSair_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           dispose();
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class menuClientes_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setInternalFrameStatus(true);
            add(new FormCadClientes(), BorderLayout.CENTER);
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class menuVeiculos_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setInternalFrameStatus(true);
            add(new FormCadVeiculos(), BorderLayout.CENTER);
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class menuPedidos_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setInternalFrameStatus(true);
            add(new FormCadPedidos(), BorderLayout.CENTER);
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class menuVendas_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setInternalFrameStatus(true);
            add(new FormVendas(), BorderLayout.CENTER);
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private void habilitarMenus(boolean b) {
        barraMenu.setEnabled(b);
    }
    
    /* -------------------------------------------------------------------- */
    
    public void setInternalFrameStatus(boolean b) {
        habilitarMenus(b);
    }
}
