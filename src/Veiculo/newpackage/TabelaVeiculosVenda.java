/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */

public class TabelaVeiculosVenda extends AbstractTableModel {
    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço para venda"};
    private final DecimalFormat moeda = new DecimalFormat("R$#,##0.00",
            new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR")));
    private List<Veiculo> lista = new ArrayList<>();

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Veiculo v = lista.get(row);
        switch (col) {
            case 0: return v.getPlaca();
            case 1: return v.getMarca();
            case 2: return nomeModelo(v);
            case 3: return String.format("%04d", v.getAno());
            case 4: return moeda.format(v.getValorParaVenda());
            default: return null;
        }
    }

    private String nomeModelo(Veiculo v) {
        if (v instanceof Automovel) return String.valueOf(((Automovel) v).getModelo());
        if (v instanceof Motocicleta) return String.valueOf(((Motocicleta) v).getModelo());
        if (v instanceof Van) return String.valueOf(((Van) v).getModelo());
        return "";
    }

    public void setLista(List<Veiculo> novaLista) {
        this.lista = new ArrayList<>(novaLista);
        fireTableDataChanged();
    }

    public Veiculo getVeiculo(int row) {
        return lista.get(row);
    }
}
