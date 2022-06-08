/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.VeiculoDAO;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Veiculo;
import util.U;

/**
 *
 * @author jaide
 */
public class FormCadVeiculos extends JInternalFrame {
    
    private GridLayout gridForm;
    private GridLayout gridControls;
    
    private JLabel rotuloNome;
    private JLabel rotuloFabricante;
    private JLabel rotuloAno;
    private JLabel rotuloCor;
    private JLabel rotuloMotorizacao;
    private JLabel rotuloQuantidade;
    private JLabel rotuloValor;

    private JTextField textoNome;
    private JTextField textoFabricante;
    private JComboBox<Integer> comboAno;
    private JComboBox<String> comboCor;
    private JComboBox<String> comboMotorizacao;
    private JTextField textoQuantidade;
    private JTextField textoValor;

    private JButton botaoCadastrar;
    private JButton botaoCancelar;
    private JButton botaoAtualizar;

    private DefaultTableModel modelo;
    private JTable tabela;
    
    /* -------------------------------------------------------------------- */
    
    public FormCadVeiculos() {
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
        gridControls = new GridLayout(7, 2);
        JPanel painel = new JPanel(gridControls);
        
        rotuloNome = new JLabel("Nome");
        rotuloFabricante = new JLabel("Fabricante");
        rotuloAno = new JLabel("Ano");
        rotuloCor = new JLabel("Cor");
        rotuloMotorizacao = new JLabel("Motorizacao");
        rotuloQuantidade = new JLabel("Quantidade");
        rotuloValor = new JLabel("Valor");
        
        textoNome = new JTextField();
        textoFabricante = new JTextField();
        
        comboAno = new JComboBox<>();
        int ano = 2000;
        
        for (int i = 0; i <= 22; i++) {
            comboAno.addItem(ano);
            ano++;
        }
        
        String[] cores = new String[] { "Azul Cobalto", "Azul Metalico", "Branco", "Prata", "Preto", "Vermelho Escarlate", "Vermelho Vivo"};
        comboCor = new JComboBox<>(cores);
        
        String[] motores = new String[] {"1.0", "1.3", "1.4", "1.5", "1.6", "1.8", "2.0", "2.2", "2.3", "2.5"};
        comboMotorizacao = new JComboBox<>(motores);
        
        textoQuantidade = new JTextField();
        textoValor = new JTextField();
        
        painel.add(rotuloNome);
        painel.add(textoNome);
        painel.add(rotuloFabricante);
        painel.add(textoFabricante);
        painel.add(rotuloAno);
        painel.add(comboAno);
        painel.add(rotuloCor);
        painel.add(comboCor);
        painel.add(rotuloMotorizacao);
        painel.add(comboMotorizacao);
        painel.add(rotuloQuantidade);
        painel.add(textoQuantidade);
        painel.add(rotuloValor);
        painel.add(textoValor);
        
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
        modelo.addColumn("Fabricante");
        modelo.addColumn("Ano");
        modelo.addColumn("Cor");
        modelo.addColumn("Motorização");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Valor");
        
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
            String fabricante = textoFabricante.getText();
            int ano = Integer.parseInt(comboAno.getSelectedItem().toString());
            String cor = comboCor.getSelectedItem().toString();
            String motor = comboMotorizacao.getSelectedItem().toString();
            int qtde = Integer.parseInt(textoQuantidade.getText());
            int valor = Integer.parseInt(textoValor.getText());
            String id = U.generateId("V");
            
            Veiculo v = new Veiculo();
            v.setId(id);
            v.setNome(nome);
            v.setFabricante(fabricante);
            v.setAno(ano);
            v.setCor(cor);
            v.setMotorizacao(motor);
            v.setQuantidade(qtde);
            v.setValor(valor);
            boolean b = VeiculoDAO.insert(v);
            
            if (b) {
                JOptionPane.showMessageDialog(null, "Cadastro realizdo com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                textoNome.setText("");
                textoFabricante.setText("");
                comboCor.setSelectedIndex(0);
                comboMotorizacao.setSelectedIndex(0);
                textoQuantidade.setText("");
                textoValor.setText("");
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
        
        List<Veiculo> listaVeiculos = VeiculoDAO.list();
        
        if (!listaVeiculos.isEmpty()) {
            for (Veiculo v : listaVeiculos) {
                modelo.addRow(new Object[]{
                    v.getId(),
                    v.getNome(),
                    v.getFabricante(),
                    v.getAno(),
                    v.getCor(),
                    v.getMotorizacao(),
                    v.getQuantidade(),
                    v.getValor()
                });
            }
        }
    }
}
