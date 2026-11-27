/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

/**
 *
 * @author Lunna
 */
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TelaDevolver extends JFrame {

    private final TabelaVeiculosLocadosModel model = new TabelaVeiculosLocadosModel();
    private final JTable tabela = new JTable(model);
    private final JButton btnDevolver = new JButton("Devolver veículo");

    public TelaDevolver() {
        setTitle("Tela 4 - Devolver Veículo");
        setSize(980, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodape = new JPanel();
        rodape.add(btnDevolver);
        add(rodape, BorderLayout.SOUTH);

        atualizarTabela();
        btnDevolver.addActionListener(e -> devolverSelecionado());
    }

    public void atualizarTabela() {
        List<Veiculo> locados = new ArrayList<>();
        for (Veiculo v : Repositorio.veiculos) {
            if (v.getEstado() == Estado.LOCADO) {
                locados.add(v);
            }
        }
        model.setDados(locados);
    }

    private void devolverSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo locado na tabela.");
            return;
        }

        Veiculo veiculo = model.getVeiculoAt(linha);
        veiculo.devolver();
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Veículo devolvido com sucesso!");
    }
}
