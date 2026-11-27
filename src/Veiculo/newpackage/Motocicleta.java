/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Veiculo.newpackage;

/**
 *
 * @author User
 */
public class Motocicleta extends Veiculo{
    ModeloMotocicleta modelo;
    
    public Motocicleta(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano) {
        super(marca, estado, locacao, categoria, valorDeCompra, placa, ano);
    }
    
    ModeloMotocicleta getModelo(){
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        if (categoria == Categoria.POPULAR) {
            return 70.00;
        } else if (categoria == Categoria.INTERMEDIARIO) {
            return 200.00;
        } else if (categoria == Categoria.LUXO) {
            return 350.00;
        }
        return 0.0;
    }
    
}
