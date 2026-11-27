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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import trabalho.Cliente;

public class TelaLocar extends JFrame {

    private final JTextField txtFiltroCliente = new JTextField(16);
    private final JComboBox<Cliente> cbClientes = new JComboBox<>();
    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"TODOS", "AUTOMOVEL", "VAN", "MOTOCICLETA"});
    private final JComboBox<String> cbMarca = new JComboBox<>();
    private final JComboBox<String> cbCategoria = new JComboBox<>();
    private final JButton btnFiltrar = new JButton("Filtrar");

    private final TabelaDisponiveisLocacaoModel model = new TabelaDisponiveisLocacaoModel();
    private final JTable tabela = new JTable(model);

    private final JTextField txtDias = new JTextField(6);
    private final JTextField txtData = new JTextField(10);
    private final JButton btnLocar = new JButton("Locar");

    public TelaLocar() {
        setTitle("Tela 3 - Locar Veículo");
        setSize(860, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cbMarca.addItem("TODAS");
        for (Marca m : Marca.values()) {
            cbMarca.addItem(m.name());
        }

        cbCategoria.addItem("TODAS");
        for (Categoria c : Categoria.values()) {
            cbCategoria.addItem(c.name());
        }

        txtData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        JButton btnBuscarCliente = new JButton("Buscar Cliente");
        JPanel linhaCliente = new JPanel();
        linhaCliente.add(new JLabel("Nome / Sobrenome / CPF:"));
        linhaCliente.add(txtFiltroCliente);
        linhaCliente.add(btnBuscarCliente);
        linhaCliente.add(new JLabel("Cliente:"));
        linhaCliente.add(cbClientes);

        JPanel linhaFiltros = new JPanel();
        linhaFiltros.add(new JLabel("Tipo:"));
        linhaFiltros.add(cbTipo);
        linhaFiltros.add(new JLabel("Marca:"));
        linhaFiltros.add(cbMarca);
        linhaFiltros.add(new JLabel("Categoria:"));
        linhaFiltros.add(cbCategoria);
        linhaFiltros.add(btnFiltrar);

        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.add(linhaCliente);
        topo.add(linhaFiltros);

        JPanel rodape = new JPanel();
        rodape.add(new JLabel("Dias:"));
        rodape.add(txtDias);
        rodape.add(new JLabel("Data (dd/MM/aaaa):"));
        rodape.add(txtData);
        rodape.add(btnLocar);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);

        carregarClientes("");
        filtrarVeiculos();

        btnBuscarCliente.addActionListener(e -> carregarClientes(txtFiltroCliente.getText().trim()));
        btnFiltrar.addActionListener(e -> filtrarVeiculos());
        btnLocar.addActionListener(e -> locarSelecionado());
    }

    private void carregarClientes(String termo) {
        cbClientes.removeAllItems();
        String busca = termo.toLowerCase();
        for (Cliente c : Repositorio.clientes) {
            boolean matchNome = c.getNome() != null && c.getNome().toLowerCase().contains(busca);
            boolean matchSobrenome = c.getSobrenome() != null && c.getSobrenome().toLowerCase().contains(busca);
            boolean matchCpf = c.getCpf() != null && c.getCpf().toLowerCase().contains(busca);
            if (busca.isEmpty() || matchNome || matchSobrenome || matchCpf) {
                cbClientes.addItem(c);
            }
        }
        cbClientes.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            String texto = value == null ? "" : value.getNome() + " " + value.getSobrenome() + " - " + value.getCpf();
            JLabel lbl = new JLabel(texto);
            if (isSelected) {
                lbl.setOpaque(true);
                lbl.setBackground(list.getSelectionBackground());
                lbl.setForeground(list.getSelectionForeground());
            }
            return lbl;
        });
    }

    private void filtrarVeiculos() {
        String tipo = (String) cbTipo.getSelectedItem();
        String marca = (String) cbMarca.getSelectedItem();
        String categoria = (String) cbCategoria.getSelectedItem();

        List<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo v : Repositorio.veiculos) {
            if (v.getEstado() != Estado.DISPONIVEL) {
                continue;
            }
            if (!"TODAS".equals(marca) && (v.getMarca() == null || !v.getMarca().name().equals(marca))) {
                continue;
            }
            if (!"TODAS".equals(categoria) && (v.getCategoria() == null || !v.getCategoria().name().equals(categoria))) {
                continue;
            }
            String classeSimples = v.getClass().getSimpleName().toUpperCase();
            if (!"TODOS".equals(tipo) && !classeSimples.equals(tipo)) {
                continue;
            }
            disponiveis.add(v);
        }
        model.setDados(disponiveis);
    }

    private void locarSelecionado() {
        Cliente cliente = (Cliente) cbClientes.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }

        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Clique em um veículo disponível na tabela.");
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(txtDias.getText().trim());
            if (dias <= 0) {
                JOptionPane.showMessageDialog(this, "Informe uma quantidade de dias maior que zero.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número inteiro válido para os dias.");
            return;
        }

        Calendar dataLocacao = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataConvertida = sdf.parse(txtData.getText().trim());
            dataLocacao.setTime(dataConvertida);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/aaaa.");
            return;
        }

        Veiculo veiculo = model.getVeiculoAt(linha);
        veiculo.locar(dias, dataLocacao, cliente);
        filtrarVeiculos();
        JOptionPane.showMessageDialog(this, "Veículo locado com sucesso!");
    }
}