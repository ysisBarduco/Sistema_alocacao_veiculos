/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

import java.util.ArrayList;
import java.util.List;
import trabalho.Cliente;

/**
 * @author Lunna
 */
public class Repositorio {
    public static final List<Cliente> clientes = new ArrayList<>();
    public static final List<Veiculo> veiculos = new ArrayList<>();

    static {
        // Exemplo rápido para teste (ajuste conforme o construtor de Cliente):
        // clientes.add(new Cliente("Maria", "Souza", "1122334", "111.222.333-44", "Av. Central, 500"));
    }
}