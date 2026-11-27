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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

public class TabelaDisponiveisLocacaoModel extends AbstractTableModel {

    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço da diária"};
    private List<Veiculo> lista = new ArrayList<>();
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

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
        switch (columnIndex) {
            case 0:
                return v.getPlaca();
            case 1:
                return v.getMarca() != null ? v.getMarca().name() : "";
            case 2:
                if (v instanceof Automovel) {
                    return ((Automovel) v).getModelo().name();
                }
                return v.getClass().getSimpleName();
            case 3:
                return String.format("%04d", v.getAno());
            case 4:
                return moeda.format(v.getValorDiariaLocacao());
            default:
                return null;
        }
    }
}
