/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.VeiculoDAO;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.Pedido;
import model.Veiculo;
import util.U;

/**
 *
 * @author jaide
 */
public class FormCadPedidos extends JInternalFrame {
    
    private GridLayout gridForm;
    private GridLayout gridControls;
    
    private JLabel rotuloIdCliente;
    private JTextField textoIdCliente;
    private JButton botaoBuscar;
    
    private JLabel rotuloDataPedido;
    private JTextField textoDataPedido;
    private JLabel rotuloDadosCliente;
    private JTextArea textoDadosCliente;
    private JScrollPane painelDadosCliente;
    
    private JLabel rotuloVeiculosDisponiveis;
    private DefaultTableModel modeloVeiculos;
    private JTable tabelaVeiculos;

    private JButton botaoGerarPedido;
    private JButton botaoCancelar;
    private Cliente cliente;
    
    /* -------------------------------------------------------------------- */
    
    public FormCadPedidos() {
        init();
    }
    
    /* -------------------------------------------------------------------- */
    
    private void init() {
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        gridForm = new GridLayout(4, 1);
        setLayout(gridForm);
        
        add(criarBuscaCliente());
        add(criarPainelControles());
        add(criarPainelListagem());
        add(criarPainelBotoes());
        
        Calendar c = Calendar.getInstance();
        String hoje = c.get(Calendar.YEAR)
                + "-" + c.get(Calendar.MONTH)
                + "-" + c.get(Calendar.DAY_OF_MONTH);
        textoDataPedido.setText(hoje);
        
        setVisible(true);
    }
    
    /* -------------------------------------------------------------------- */
    
    private JPanel criarBuscaCliente() {
        JPanel painel = new JPanel(new GridLayout(1, 3));
        
        rotuloIdCliente = new JLabel("ID do cliente");
        textoIdCliente = new JTextField();
        
        botaoBuscar = new JButton("Buscar ...");
        botaoBuscar.addActionListener(new BotaoBuscarCliente_OnClick());
        
        painel.add(rotuloIdCliente);
        painel.add(textoIdCliente);
        painel.add(botaoBuscar);
        painel.setBorder(BorderFactory.createEtchedBorder());
        
        return painel;
    }
            
    /* -------------------------------------------------------------------- */
    
    private JPanel criarPainelControles() {
        gridControls = new GridLayout(3, 2);
        JPanel painel = new JPanel(gridControls);
        
        rotuloDadosCliente = new JLabel("Dados do cliente");
        rotuloDataPedido = new JLabel("Data do pedido");
        
        textoDadosCliente = new JTextArea();
        textoDadosCliente.setEditable(false);
        painelDadosCliente = new JScrollPane(
                textoDadosCliente,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        textoDataPedido = new JTextField();
        textoDataPedido.setEditable(false);
        
        painel.add(rotuloDadosCliente);
        painel.add(painelDadosCliente);
        painel.add(rotuloDataPedido);
        painel.add(textoDataPedido);
                
        painel.setBorder(BorderFactory.createEtchedBorder());
        
        return painel;
    }
    
    /* -------------------------------------------------------------------- */
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();
        
        botaoGerarPedido = new JButton("Gerar pedido");
        botaoGerarPedido.addActionListener(new BotaoGerarPedido_OnClick());
        
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new BotaoCancelar_OnClick());
        
        painel.add(botaoGerarPedido);
        painel.add(botaoCancelar);
        
        painel.setBorder(BorderFactory.createEtchedBorder());
        
        return painel;
    }
    
    /* -------------------------------------------------------------------- */
    
    private JPanel criarPainelListagem() {
        JPanel painel = new JPanel(new GridLayout(3, 1));

        rotuloVeiculosDisponiveis = new JLabel("Veículos disponíveis");
        
        // modelo
        modeloVeiculos = new DefaultTableModel();
        tabelaVeiculos = new JTable(modeloVeiculos);
        
        modeloVeiculos.addColumn("ID");
        modeloVeiculos.addColumn("Descrição");
        modeloVeiculos.addColumn("Quantidade");
        modeloVeiculos.addColumn("Valor");
        
        JScrollPane sPane = new JScrollPane(
                tabelaVeiculos, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        painel.add(rotuloVeiculosDisponiveis);
        painel.add(sPane);
        
        atualizarListagemVeiculos();
        
        return painel;
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoBuscarCliente_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new DialogBuscaCliente();
            Object obj = U.dados;
            
            if (obj.getClass() == Cliente.class) {
                cliente = (Cliente) obj;
                textoIdCliente.setText(cliente.getId());
                String msg = cliente.getNome() + "\n"
                        + cliente.getEndereco() + "\n"
                        + cliente.getTelefone();
                textoDadosCliente.setText(msg);
            }
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoGerarPedido_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tabelaVeiculos.getSelectedRow();
            if (row >= 0) {
                String numPedido = U.generateId("P");
                String idCliente = textoIdCliente.getText();
                String dataPedido = textoDataPedido.getText();
                
                Pedido p = new Pedido();
                p.setNumero(numPedido);
                p.setIdCliente(idCliente);
                p.setDataPedido(Date.valueOf(dataPedido));
                
                String id = tabelaVeiculos.getModel().getValueAt(row, 0).toString();
                Veiculo v = VeiculoDAO.getById(id);
                
                new DialogPedido(p, v, cliente);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha para prosseguir!", "Atenção", JOptionPane.WARNING_MESSAGE);
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
    
    private void atualizarListagemVeiculos() {
        // preeche o modelo se houver dados no cadastro
        modeloVeiculos.setNumRows(0);
        
        List<Veiculo> listaVeiculos = VeiculoDAO.list();
        
        if (!listaVeiculos.isEmpty()) {
            for (Veiculo v : listaVeiculos) {
                String descricao = v.getAno() + " " + v.getFabricante()
                        + " " + v.getNome() + " " + v.getMotorizacao()
                        + " " + v.getCor();
                
                modeloVeiculos.addRow(new Object[]{
                    v.getId(),
                    descricao,
                    v.getQuantidade(),
                    v.getValor()
                });
            }
        }
    }
    
}
