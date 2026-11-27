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
    
    public Motocicleta(Marca marca, Estado estado, Categoria categoria, double valorDeCompra,
                       String placa, int ano, ModeloMotocicleta modelo) {
        super(marca, estado, null, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
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
