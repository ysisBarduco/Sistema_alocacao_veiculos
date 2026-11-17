/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class tabelaClientes extends AbstractTableModel {
    private String[] colunas = new String[]{"Nome", "Sobrenome", "RG", "CPF", "Endereço"};
    private List<Cliente> lista = new ArrayList();
    
    public tabelaClientes() {
    }

    @Override
    public int getRowCount() {
        return this.lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = lista.get(rowIndex);
        switch(columnIndex){
            case 0:
               return cliente.getNome();
            case 1:
                return cliente.getSobrenome();
            case 2:
                return cliente.getRg();
            case 3:
                return cliente.getCpf();
            case 4:
                return cliente.getEndereco();
            default:
                return null;
        }
    }
    
}
