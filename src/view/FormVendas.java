/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ClienteDAO;
import controller.PedidoDAO;
import controller.VeiculoDAO;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.Pedido;
import model.PedidoDetalhe;
import model.Veiculo;

/**
 *
 * @author jaide
 */
public class FormVendas extends JInternalFrame {
    
    private JLabel rotuloTitulo;
    private DefaultTableModel modelo;
    private JTable tabela;
    private BorderLayout bLayout;
    private JButton botaoGerarPdf;
    private JButton botaoFechar;
    
    /* -------------------------------------------------------------------- */
    
    public FormVendas() {
        init();
        criarTitulo();
        criarTabelaVendas();
        criarBotoes();
        atualizarListagem();
        setVisible(true);
    }
    
    /* -------------------------------------------------------------------- */
    
    private void init() {
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        bLayout = new BorderLayout();
        setLayout(bLayout);
    }
    
    /* -------------------------------------------------------------------- */
    
    private void criarTitulo() {
        rotuloTitulo = new JLabel("Vendas realizadas");
        rotuloTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        rotuloTitulo.setBorder(BorderFactory.createEtchedBorder());
        
        add(rotuloTitulo, BorderLayout.NORTH);
    }
    
    /* -------------------------------------------------------------------- */
    
    private void criarTabelaVendas() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Numero do pedido");
        modelo.addColumn("Nome do cliente");
        modelo.addColumn("Descrição do Pedido");
        modelo.addColumn("Data");
        modelo.addColumn("Valor");
        
        tabela = new JTable(modelo);
        JScrollPane painelTabela = new JScrollPane(
                tabela,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        add(painelTabela, BorderLayout.CENTER);
    }
    
    /* -------------------------------------------------------------------- */
    
    private void criarBotoes() {
        JPanel painel = new JPanel();
        
        botaoGerarPdf = new JButton("Gerar Arquivo para impressão");
        botaoGerarPdf.addActionListener(new BotaoGerarPdf_OnClick());
        
        botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(new BotaoFechar_OnClick());
        
        painel.add(botaoGerarPdf);
        painel.add(botaoFechar);
        
        add(painel, BorderLayout.SOUTH);
    }
    
    /* -------------------------------------------------------------------- */
    
    private void atualizarListagem() {
        modelo.setNumRows(0);
        List<Pedido> listaP = PedidoDAO.listaPedidos();
        
        if (!listaP.isEmpty()) {
            for (Pedido p : listaP) {
                Cliente c = ClienteDAO.getById(p.getIdCliente());
                PedidoDetalhe d = PedidoDAO.getDetalheById(p.getNumero());
                Veiculo v = VeiculoDAO.getById(d.getIdVeiculo());
                
                modelo.addRow(new Object[]{
                    p.getNumero(),
                    c.getNome(),
                    v.getAno() + " " + v.getFabricante()
                        + " " + v.getNome() + " " + v.getMotorizacao()
                        + " " + v.getCor(),
                    p.getDataPedido(),
                    v.getValor()
                });
            }
        }
        
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoGerarPdf_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pedido = "None";
            String nomeCliente = "None";
            String descricao = "None";
            String data = "None";
            String valor = "None";
            
            int row = tabela.getSelectedRow();
            if (row >= 0) {
                pedido = tabela.getModel().getValueAt(row, 0).toString();
                nomeCliente = tabela.getModel().getValueAt(row, 1).toString();
                descricao = tabela.getModel().getValueAt(row, 2).toString();
                data = tabela.getModel().getValueAt(row, 3).toString();
                valor = tabela.getModel().getValueAt(row, 4).toString();
                
                String saida =  "VENDA\n";
                       saida += "------------------------------\n\n";
                       saida += "Pedido .....:  " + pedido + "\n";
                       saida += "Cliente ....:  " + nomeCliente + "\n";
                       saida += "Descrição ..:  " + descricao + "\n";
                       saida += "Data .......:  " + data + "\n";
                       saida += "Valor ......:  R$ " + valor;
                
                try {
                    FileOutputStream fileOutput = new FileOutputStream("saida.txt");
                    PrintWriter out = new PrintWriter(fileOutput);

                    out.print(saida);
                    out.flush();

                    out.close();
                    fileOutput.close();
                    
                    JOptionPane.showMessageDialog(null, "Arquivo gerado com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FormVendas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FormVendas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        }
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoFechar_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
