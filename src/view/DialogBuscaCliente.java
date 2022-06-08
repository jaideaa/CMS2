/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ClienteDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import util.U;

/**
 *
 * @author jaide
 */
public class DialogBuscaCliente extends JDialog {
    
    private JLabel rotuloTitulo;
    private DefaultTableModel modelo;
    private JTable tabela;
    private JScrollPane painelListagem;
    private JButton botaoSelecionar;
    private JButton botaoCancelar;
    
    /* -------------------------------------------------------------------- */
    
    public DialogBuscaCliente() {
        setSize(600, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setModal(true);
        
        rotuloTitulo = new JLabel("Lista de clientes cadastrados:");
        rotuloTitulo.setBounds(5, 5, 200, 30);
        
        painelListagem = criarPainelListagem();
        painelListagem.setBounds(5, 35, 575, 270);
        
        botaoSelecionar = new JButton("Selecionar");
        botaoSelecionar.addActionListener(new BotaoSelecionar_OnClick());
        
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new BotaoCancelar_OnClick());
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBounds(5, 310, 575, 40);
        painelBotoes.setBorder(BorderFactory.createEtchedBorder());
        
        painelBotoes.add(botaoSelecionar);
        painelBotoes.add(botaoCancelar);

        add(rotuloTitulo);
        add(painelListagem);
        add(painelBotoes);
        
        setVisible(true);
    }
    
    /* -------------------------------------------------------------------- */
    
    private JScrollPane criarPainelListagem() {
        // modelo
        modelo = new DefaultTableModel();
        tabela = new JTable(modelo);
        
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Enedereço");
        modelo.addColumn("Telefone");
        
        JScrollPane sPane = new JScrollPane(
                tabela, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        atualizarListagem();
        
        return sPane;
    }
    
    /* -------------------------------------------------------------------- */
    
    private void atualizarListagem() {
        // preeche o modelo se houver dados no cadastro
        modelo.setNumRows(0);
        
        List<Cliente> listaClientes = ClienteDAO.list();
        
        if (!listaClientes.isEmpty()) {
            for (Cliente c : listaClientes) {
                modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getEndereco(),
                    c.getTelefone()
                });
            }
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoSelecionar_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cliente c = new Cliente();
            int row = tabela.getSelectedRow();
            
            if (row >= 0) {
                c.setId(tabela.getModel().getValueAt(row, 0).toString());
                c.setNome(tabela.getModel().getValueAt(row, 1).toString());
                c.setEndereco(tabela.getModel().getValueAt(row, 2).toString());
                c.setTelefone(tabela.getModel().getValueAt(row, 3).toString());
                U.dados = c;
            }
            
            dispose();
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoCancelar_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
