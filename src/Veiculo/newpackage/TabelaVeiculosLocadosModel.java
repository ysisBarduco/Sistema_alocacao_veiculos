/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

/**
 *
 * @author Lunna
 */
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

public class TabelaVeiculosLocadosModel extends AbstractTableModel {

    private final String[] colunas = {
        "Nome do Cliente", "Placa", "Marca", "Modelo", "Ano",
        "Data Locação", "Preço Diária", "Quantidade de dias locado", "Valor Locação"
    };
    private List<Veiculo> lista = new ArrayList<>();
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    public void setDados(List<Veiculo> novosDados) {
        this.lista = novosDados;
        fireTableDataChanged();
    }

    public Veiculo getVeiculoAt(int linha) {
        if (linha >= 0 && linha < lista.size()) {
            return lista.get(linha);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veiculo v = lista.get(rowIndex);
        Locacao loc = v.getLocacao();
        switch (columnIndex) {
            case 0:
                if (loc != null && loc.getCliente() != null) {
                    return loc.getCliente().getNome() + " " + loc.getCliente().getSobrenome();
                }
                return "";
            case 1:
                return v.getPlaca();
            case 2:
                return v.getMarca() != null ? v.getMarca().name() : "";
            case 3:
                if (v instanceof Automovel) {
                    return ((Automovel) v).getModelo().name();
                }
                return v.getClass().getSimpleName();
            case 4:
                return String.format("%04d", v.getAno());
            case 5:
                return (loc != null && loc.getData() != null) ? formatoData.format(loc.getData().getTime()) : "";
            case 6:
                return moeda.format(v.getValorDiariaLocacao());
            case 7:
                return loc != null ? loc.getDias() : 0;
            case 8:
                return loc != null ? moeda.format(loc.getValor()) : moeda.format(0);
            default:
                return null;
        }
    }
}