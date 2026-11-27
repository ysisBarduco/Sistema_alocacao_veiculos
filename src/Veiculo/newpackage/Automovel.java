/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

/**
 *
 * @author Lunna
 */
public class Automovel extends Veiculo {

    private ModeloAutomovel modelo;

    public Automovel(Marca marca, Estado estado, Locacao locacao, Categoria categoria,
                     double valorDeCompra, String placa, int ano, ModeloAutomovel modelo) {
        super(marca, estado, locacao, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloAutomovel getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        if (categoria == Categoria.POPULAR) {
            return 100.00;
        } else if (categoria == Categoria.INTERMEDIARIO) {
            return 300.00;
        } else if (categoria == Categoria.LUXO) {
            return 450.00;
        }
        return 0.0;
    }
}
