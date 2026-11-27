/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author User
 */
public class TelaIncluirVeiculo extends JFrame {

    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Automóvel", "Motocicleta", "Van"});
    private final JComboBox<Marca> comboMarca = new JComboBox<>(Marca.values());
    private final JComboBox<Categoria> comboCategoria = new JComboBox<>(Categoria.values());
    private final JComboBox<Estado> comboEstado =
            new JComboBox<>(new Estado[]{Estado.NOVO, Estado.DISPONIVEL});
    private final JFormattedTextField inputValor;
    private final JFormattedTextField inputPlaca;
    private final JFormattedTextField inputAno;
    private final JButton buttonIncluir = new JButton("Incluir");

    public TelaIncluirVeiculo() {
        setTitle("Novo veículo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        inputValor = new JFormattedTextField(moeda);
        inputValor.setColumns(12);

        try {
            inputPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
            inputAno = new JFormattedTextField(new MaskFormatter("####"));
        } catch (java.text.ParseException ex) {
            throw new IllegalStateException(ex);
        }

        JLabel titulo = new JLabel("Novo veículo", SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        form.add(new JLabel("Tipo"));        form.add(comboTipo);
        form.add(new JLabel("Marca"));       form.add(comboMarca);
        form.add(new JLabel("Categoria"));   form.add(comboCategoria);
        form.add(new JLabel("Estado"));      form.add(comboEstado);
        form.add(new JLabel("Ano"));         form.add(inputAno);
        form.add(new JLabel("Placa"));       form.add(inputPlaca);
        form.add(new JLabel("Valor")); form.add(inputValor);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rodape.add(buttonIncluir);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titulo, BorderLayout.NORTH);
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(rodape, BorderLayout.SOUTH);

        comboTipo.addActionListener(e -> atualizarModelos());
        buttonIncluir.addActionListener(e -> incluir());
        atualizarModelos();

        pack();
        setLocationRelativeTo(null);
    }

    private void atualizarModelos() {
        Object[] itens;
        switch ((String) comboTipo.getSelectedItem()) {
            case "Automóvel":   itens = ModeloAutomovel.values();   break;
            case "Motocicleta": itens = ModeloMotocicleta.values(); break;
            default:            itens = ModeloVan.values();         break;
        }
        comboModelo.setModel(new DefaultComboBoxModel<>(itens));
    }

    private void incluir() {
        try {
            Marca marca = (Marca) comboMarca.getSelectedItem();
            Estado estado = (Estado) comboEstado.getSelectedItem();
            Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
            Object modelo = comboModelo.getSelectedItem();

            String placa = inputPlaca.getText();
            if (!placa.matches("[A-Z]{3}-\\d{4}")) {
                aviso("Placa inválida. Use o formato XXX-0000.");
                return;
            }

            inputAno.commitEdit();
            String anoTxt = inputAno.getText().trim();
            if (!anoTxt.matches("\\d{4}")) {
                aviso("Ano inválido. Informe 4 dígitos.");
                return;
            }
            int ano = Integer.parseInt(anoTxt);

            inputValor.commitEdit();
            Object valorObj = inputValor.getValue();
            if (valorObj == null) {
                aviso("Informe um valor.");
                return;
            }
            double valorCompra = ((Number) valorObj).doubleValue();
            if (valorCompra <= 0) {
                aviso("O valor deve ser maior que zero.");
                return;
            }

            for (Veiculo existente : Repositorio.veiculos) {
                if (existente.getPlaca().equals(placa)) {
                    aviso("Já existe um veículo com essa placa.");
                    return;
                }
            }

            Veiculo v;
            if (modelo instanceof ModeloAutomovel) {
                v = new Automovel(marca, estado, categoria, valorCompra, placa, ano, (ModeloAutomovel) modelo);
            } else if (modelo instanceof ModeloMotocicleta) {
                v = new Motocicleta(marca, estado, categoria, valorCompra, placa, ano, (ModeloMotocicleta) modelo);
            } else {
                v = new Van(marca, estado, categoria, valorCompra, placa, ano, (ModeloVan) modelo);
            }

            Repositorio.veiculos.add(v);
            JOptionPane.showMessageDialog(this, "Veículo incluído.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            inputPlaca.setValue(null);
            inputAno.setValue(null);
            inputValor.setValue(null);

        } catch (java.text.ParseException ex) {
            aviso("Valor ou ano inválido.");
        }
    }

    private void aviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaIncluirVeiculo().setVisible(true));
    }
}
