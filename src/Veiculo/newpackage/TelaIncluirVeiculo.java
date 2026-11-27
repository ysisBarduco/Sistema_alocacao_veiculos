package Veiculo.newpackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author User
 */

public class TelaIncluirVeiculo extends JFrame {

    private final JComboBox<String> comboTipo =
            new JComboBox<>(new String[]{"Automóvel", "Motocicleta", "Van"});
    private final JComboBox<Object> comboModelo = new JComboBox<>();
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

        DecimalFormat df = new DecimalFormat("#,##0.00",
                new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR")));
        NumberFormatter nf = new NumberFormatter(df);
        nf.setValueClass(Double.class);
        nf.setMinimum(0.0);
        nf.setAllowsInvalid(false);
        nf.setCommitsOnValidEdit(true);
        inputValor = new JFormattedTextField(nf);
        inputValor.setValue(0.0);
        inputValor.setColumns(12);

        try {
            inputPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
            inputAno = new JFormattedTextField(new MaskFormatter("####"));
        } catch (ParseException ex) {
            throw new IllegalStateException(ex);
        }

        JLabel titulo = new JLabel("Novo veículo", SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        form.add(new JLabel("Tipo"));              form.add(comboTipo);
        form.add(new JLabel("Marca"));             form.add(comboMarca);
        form.add(new JLabel("Modelo"));            form.add(comboModelo);
        form.add(new JLabel("Categoria"));         form.add(comboCategoria);
        form.add(new JLabel("Estado"));            form.add(comboEstado);
        form.add(new JLabel("Ano"));               form.add(inputAno);
        form.add(new JLabel("Placa"));             form.add(inputPlaca);
        form.add(new JLabel("Valor de compra (R$)")); form.add(inputValor);

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
        Marca marca = (Marca) comboMarca.getSelectedItem();
        Estado estado = (Estado) comboEstado.getSelectedItem();
        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
        Object modelo = comboModelo.getSelectedItem();

        String placa = inputPlaca.getText();
        if (!placa.matches("[A-Z]{3}-\\d{4}")) {
            aviso("Placa inválida. Use o formato XXX-0000.");
            return;
        }

        String anoTxt = inputAno.getText().trim();
        if (!anoTxt.matches("\\d{4}")) {
            aviso("Ano inválido. Informe 4 dígitos.");
            return;
        }
        int ano = Integer.parseInt(anoTxt);

        Object valorObj = inputValor.getValue();
        double valorCompra = (valorObj instanceof Number) ? ((Number) valorObj).doubleValue() : 0;
        if (valorCompra <= 0) {
            aviso("Informe um valor de compra maior que zero.");
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
            v = new Automovel(marca, estado, null, categoria, valorCompra, placa, ano, (ModeloAutomovel) modelo);
        } else if (modelo instanceof ModeloMotocicleta) {
            v = new Motocicleta(marca, estado, null, categoria, valorCompra, placa, ano, (ModeloMotocicleta) modelo);
        } else {
            v = new Van(marca, estado, null, categoria, valorCompra, placa, ano, (ModeloVan) modelo);
        }

        Repositorio.veiculos.add(v);
        JOptionPane.showMessageDialog(this, "Veículo incluído.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limparFormulario();
    }

    private void limparFormulario() {
        inputPlaca.setValue(null);
        inputAno.setValue(null);
        inputValor.setValue(0.0);
    }

    private void aviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaIncluirVeiculo().setVisible(true));
    }
}