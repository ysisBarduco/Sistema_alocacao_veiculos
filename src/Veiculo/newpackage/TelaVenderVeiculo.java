/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author User
 */
public class TelaVenderVeiculo extends JFrame {
    private static final String TODOS = "Todos";

    private final JComboBox<Object> comboTipo = new JComboBox<>();
    private final JComboBox<Object> comboMarca = new JComboBox<>();
    private final JComboBox<Object> comboCategoria = new JComboBox<>();
    private final TabelaVeiculosVenda tabelaModel = new TabelaVeiculosVenda();
    private final JTable tabela = new JTable(tabelaModel);
    private final JButton buttonVender = new JButton("Vender");

    public TelaVenderVeiculo() {
        setTitle("Vender veículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        comboTipo.setModel(new DefaultComboBoxModel<>(
                new Object[]{TODOS, "Automóvel", "Van", "Motocicleta"}));
        comboMarca.setModel(modeloComTodos(Marca.values()));
        comboCategoria.setModel(modeloComTodos(Categoria.values()));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        filtros.add(new JLabel("Tipo"));      filtros.add(comboTipo);
        filtros.add(new JLabel("Marca"));     filtros.add(comboMarca);
        filtros.add(new JLabel("Categoria")); filtros.add(comboCategoria);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setFillsViewportHeight(true);
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        tabela.getColumnModel().getColumn(4).setCellRenderer(direita);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        rodape.add(buttonVender);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filtros, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(tabela), BorderLayout.CENTER);
        getContentPane().add(rodape, BorderLayout.SOUTH);

        comboTipo.addActionListener(e -> atualizarTabela());
        comboMarca.addActionListener(e -> atualizarTabela());
        comboCategoria.addActionListener(e -> atualizarTabela());
        buttonVender.addActionListener(e -> vender());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                atualizarTabela();
            }
        });

        atualizarTabela();
        setSize(650, 400);
        setLocationRelativeTo(null);
    }

    private DefaultComboBoxModel<Object> modeloComTodos(Object[] valores) {
        DefaultComboBoxModel<Object> m = new DefaultComboBoxModel<>();
        m.addElement(TODOS);
        for (Object o : valores) {
            m.addElement(o);
        }
        return m;
    }

    private boolean confereTipo(Veiculo v, Object tipo) {
        if (!(tipo instanceof String) || TODOS.equals(tipo)) return true;
        switch ((String) tipo) {
            case "Automóvel":   return v instanceof Automovel;
            case "Van":         return v instanceof Van;
            case "Motocicleta": return v instanceof Motocicleta;
            default:            return true;
        }
    }

    private void atualizarTabela() {
        Object tipo = comboTipo.getSelectedItem();
        Object marca = comboMarca.getSelectedItem();
        Object categoria = comboCategoria.getSelectedItem();

        List<Veiculo> filtrados = new ArrayList<>();
        for (Veiculo v : Repositorio.veiculos) {
            if (v.getEstado() != Estado.DISPONIVEL) continue;
            if (!confereTipo(v, tipo)) continue;
            if (marca instanceof Marca && v.getMarca() != marca) continue;
            if (categoria instanceof Categoria && v.getCategoria() != categoria) continue;
            filtrados.add(v);
        }
        tabelaModel.setLista(filtrados);
    }

    private void vender() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Veiculo v = tabelaModel.getVeiculo(tabela.convertRowIndexToModel(linha));

        int opcao = JOptionPane.showConfirmDialog(this,
                "Confirmar a venda do veículo " + v.getPlaca() + "?",
                "Confirmar venda", JOptionPane.YES_NO_OPTION);
        if (opcao != JOptionPane.YES_OPTION) return;

        v.vender();
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Veículo vendido.",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaVenderVeiculo().setVisible(true));
    }
}
