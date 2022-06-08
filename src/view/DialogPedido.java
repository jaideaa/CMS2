/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.PedidoDAO;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import model.Cliente;
import model.Pedido;
import model.PedidoDetalhe;
import model.Veiculo;

/**
 *
 * @author jaide
 */
public class DialogPedido extends JDialog {
    
    private JLabel rotulotitulo;
    private JTextArea textPedido;
    private JScrollPane painelPedido;
    
    private JButton botaoEfetivarVenda;
    private JButton botaoCancelar;
    
    private Cliente cliente;
    private Pedido pedido;
    private Veiculo veiculo;
    
    /* -------------------------------------------------------------------- */
    
    public DialogPedido(Pedido p, Veiculo v, Cliente c) {
        setSize(600, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setModal(true);
        
        init(p, v, c);
        
        cliente = c;
        pedido = p;
        veiculo = v;
        
        setVisible(true);
    }
    
    /* -------------------------------------------------------------------- */
    
    private void init(Pedido p, Veiculo v, Cliente c) {
        // rotulo de titulo
        
        rotulotitulo = new JLabel("CONFIRMAÇÃO DE VENDA");
        rotulotitulo.setBounds(10, 10, 565, 40);
        rotulotitulo.setBorder(BorderFactory.createEtchedBorder());
        rotulotitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Caixa de texto do pedido
        
        String msg = "PEDIDO\n";
              msg += "------";
              msg += "\n\n";
              msg += "Número do pedido ..:  " + p.getNumero() + "\n";
              msg += "Cliente ...........:  " + c.getId() + " - " + c.getNome() + "\n";
              msg += "                      " + c.getEndereco() + "\n";
              msg += "                      " + c.getTelefone() + "\n";
              
              msg += "\n\n";
              msg += "Detalhes do pedido:\n";
              msg += "-------------------\n";
              msg += v.getAno() + " " + v.getFabricante()
                        + " " + v.getNome() + " " + v.getMotorizacao()
                        + " " + v.getCor() + "\n";
              msg += "\n";
              msg += "Quantidade: ......:  01\n"; 
              msg += "TOTAL ............:  R$ " + v.getValor();
              
        textPedido = new JTextArea(msg);
        textPedido.setFont(new Font("Consolas", Font.PLAIN, 12));
        painelPedido = new JScrollPane(
                textPedido, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        textPedido.setEditable(false);
        painelPedido.setBounds(10, 55, 565, 250);
        
        // Botoes
        
        botaoEfetivarVenda = new JButton("Efetivar venda");
        botaoEfetivarVenda.addActionListener(new BotaoEfetivarVenda_OnClick());
        
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new BotaoCancelar_OnClick());
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBounds(10, 315, 565, 40);
        painelBotoes.setBorder(BorderFactory.createEtchedBorder());
        
        painelBotoes.add(botaoEfetivarVenda);
        painelBotoes.add(botaoCancelar);
        
        // Adiciona ao formulario
        
        add(rotulotitulo);
        add(painelPedido);
        add(painelBotoes);
    }
    
    /* -------------------------------------------------------------------- */
    
    private class BotaoEfetivarVenda_OnClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PedidoDetalhe d = new PedidoDetalhe();
            d.setIdVeiculo(veiculo.getId());
            d.setNumeroPedido(pedido.getNumero());
            
            boolean bPedido = PedidoDAO.insert(pedido, d);
            
            if (bPedido) {
                JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao tentar efetivar a venda!", "Erro", JOptionPane.ERROR_MESSAGE);
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
}
