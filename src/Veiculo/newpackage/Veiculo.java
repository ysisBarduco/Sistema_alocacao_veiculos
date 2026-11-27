/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lunna
 */
package Veiculo.newpackage;

import java.util.Calendar;
import trabalho.Cliente;

public abstract class Veiculo {
    protected Marca marca;
    protected Estado estado;
    protected Locacao locacao;
    protected Categoria categoria;
    protected double valorDeCompra;
    protected String placa;
    protected int ano;

    public Veiculo(Marca marca, Estado estado, Locacao locacao, Categoria categoria,
                   double valorDeCompra, String placa, int ano) {
        this.marca = marca;
        this.estado = estado;
        this.locacao = locacao;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
    }

    public void locar(int dias, Calendar data, Cliente cliente) {
        if (this.estado == Estado.VENDIDO || this.estado == Estado.LOCADO) {
            throw new IllegalStateException("Veículo indisponível para locação.");
        }
        double valorTotal = dias * getValorDiariaLocacao();
        this.locacao = new Locacao(dias, valorTotal, data, cliente);
        this.estado = Estado.LOCADO;
    }

    public void vender() {
        if (this.estado == Estado.LOCADO) {
            throw new IllegalStateException("Não é possível vender um veículo locado.");
        }
        this.estado = Estado.VENDIDO;
    }

    public void devolver() {
        this.locacao = null;
        this.estado = Estado.DISPONIVEL;
    }

    public Estado getEstado() {
        return estado;
    }

    public Marca getMarca() {
        return marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Locacao getLocacao() {
        return locacao;
    }

    public String getPlaca() {
        return placa;
    }

    public int getAno() {
        return ano;
    }

    public double getValorParaVenda() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int idadeVeiculoEmAnos = Math.max(0, anoAtual - this.ano);
        double valorParaVenda = this.valorDeCompra - (idadeVeiculoEmAnos * 0.15 * this.valorDeCompra);
        double dezPorCento = this.valorDeCompra * 0.10;
        if (valorParaVenda < dezPorCento || valorParaVenda < 0) {
            return dezPorCento;
        }
        return valorParaVenda;
    }

    public abstract double getValorDiariaLocacao();
}
