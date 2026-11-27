package trabalho;

import Veiculo.newpackage.Repositorio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;

class TabelaClientesTest {

    @BeforeEach
    void prepararRepositorio() {
        Repositorio.clientes.clear();
    }

    @AfterEach
    void limparRepositorio() {
        Repositorio.clientes.clear();
    }

    @Test
    void clientesAdicionadosFicamDisponiveisNoRepositorioCompartilhado() {
        Cliente cliente = new Cliente("Ana", "Silva", "123", "456", "Rua A");
        TabelaClientes tabela = new TabelaClientes();

        tabela.AdicionarCliente(cliente);

        assertSame(cliente, Repositorio.clientes.get(0));
        assertSame(cliente, new TabelaClientes().getDados(0));
    }
}
