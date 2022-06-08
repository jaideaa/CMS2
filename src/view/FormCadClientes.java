/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ClienteDAO;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import util.U;

/**
 *
 * @author jaide
 */
public class FormCadClientes extends JInternalFrame {
    
    private GridLayout gridForm;
    private GridLayout gridControls;
    
    private JLabel rotuloNome;
    private JLabel rotuloEndereco;
    private JLabel rotuloTelefone;
    
    private JTextField textoNome;
    private JTextField textoEndereco;
    private JTextField textoTelefone;
    
    private JButton botaoCadastrar;
    private JButton botaoCancelar;
    
    private DefaultTableModel modelo;
    private JTable tabela;
    
    /* -------------------------------------------------------------------- */
    
    public FormCadClientes() {
        init();
    }
    
    /* -------------------------------------------------------------------- */
    
    private void init() {
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        gridForm = new GridLayout(3, 1);
        setLayout(gridForm);
        
        add(criarPainelControles());
        add(criarPainelBotoes());
        add(criarPainelListagem());
        
        setVisible(true);
    }
    
    /* -------------------------------------------------------------------- */
    
    private JPanel criarPainelControles() {
        gridControls = new GridLayout(3, 2);
        JPanel painel = new JPanel(gridControls);
        
        rotuloNome = new JLabel("Nome");
        rotuloEndereco = new JLabel("Endereço");
        rotuloTelefone = new JLabel("Telefone");
        
        textoNome = new JTextField();
        textoEndereco = new JTextField();
        textoTelefone = new JTextField();        
        
        painel.add(rotuloNome);
        painel.add(textoNome);
        painel.add(rotuloEndereco);
        painel.add(textoEndereco);
        painel.add(rotuloTelefone);
        painel.add(textoTelefone);
        
        painel.setBorder(BorderFactory.createEtchedBorder());
        
        return painel;
    }
    
    /* -------------------------------------------------------------------- */
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();
        
        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.addActionListener(new BotaoCadastrar_OnClick());
        
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new BotaoCancelar_OnClick());
        
        painel.add(botaoCadastrar);
        painel.add(botaoCancelar);
        
        painel.setBorder(BorderFactory.createEtchedBorder());
        
        return painel;
    }
    
    /* -------------------------------------------------------------------- */
    
    private JScrollPane criarPainelListagem() {
        // modelo
        modelo = new DefaultTableModel();
        tabela = new JTable(modelo);
        
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Endereço");
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
    
    private class BotaoCadastrar_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = textoNome.getText();
            String endereco = textoEndereco.getText();
            String telefone = textoTelefone.getText();
            String id = U.generateId("C");
            
            Cliente c = new Cliente();
            c.setId(id);
            c.setNome(nome);
            c.setEndereco(endereco);
            c.setTelefone(telefone);
            boolean b = ClienteDAO.insert(c);
            
            if (b) {
                JOptionPane.showMessageDialog(null, "Cadastro realizdo com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                textoNome.setText("");
                textoEndereco.setText("");
                textoTelefone.setText("");
                atualizarListagem();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar!", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoCancelar_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
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
}
