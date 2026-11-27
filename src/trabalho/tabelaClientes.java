package trabalho;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ysis
 * @author Luana
 */
public class TabelaClientes extends AbstractTableModel {
    private String[] colunas = new String[]{"Nome", "Sobrenome", "RG", "CPF", "Endereço"};
    private List<Cliente> lista = new ArrayList();
    
    public TabelaClientes() {
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
    public String getColumnName(int index) {
        return this.colunas[index];
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
    
    public boolean removerCliente(Cliente c){
        int linha = this.lista.indexOf(c);
        boolean resultado = this.lista.remove(c);
        this.fireTableRowsDeleted(linha, linha); //Atualiza a JTable
        return resultado;
    }
    
    public void AdicionarCliente(Cliente c){
        this.lista.add(c);
        this.fireTableRowsInserted(lista.size()-1, lista.size()-1); //Atualiza a JTable
    }
    
    public List<Cliente> getLista(){
        return lista;
    }
    
    public Cliente getDados(int linha){
        return lista.get(linha);
    }
    
    public void setLista(List<Cliente> clientes){
        this.lista = clientes;
        this.fireTableDataChanged();
    }
    
    public Cliente getSelecionado(int linha){
        return lista.get(linha);
    }
    
    public void removerClientes(List<Cliente> selecionadosExcluir){
        selecionadosExcluir.forEach((cliente) -> {
            removerCliente(cliente);
        });
    }
}
