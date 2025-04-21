package SistemaBrownie.Modelo;

import java.io.Serializable;
import java.util.Objects;

public class Brownie implements Serializable {
    private String sabor;
    private double preco;
    private TipoBrownie tipo;

    public Brownie(String sabor, double preco, TipoBrownie tipo){
        this.sabor = sabor;
        this.preco = preco;
        this.tipo = tipo;
    }
    public boolean ehDoTipo(TipoBrownie tipo){
        return this.tipo == tipo;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brownie brownie = (Brownie) o;
        return Double.compare(brownie.preco, preco) == 0 && Objects.equals(sabor, brownie.sabor) && Objects.equals(tipo, brownie.tipo);
    }

    public int hashCode(){
        return Objects.hash(sabor, preco, tipo);
    }

    public String toString(){
        return "Brownie(" + "sabor=" + sabor + ", preco=" + preco + ", tipo" + tipo + ')';
    }

    public String getSabor(){
        return sabor;
    }

    public void setSabor(String sabor){
        this.sabor = sabor;
    }

    public double getPreco(){
        return preco;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public TipoBrownie getTipo(){
        return tipo;
    }

    public void setTipo(TipoBrownie tipo){
        this.tipo = tipo;
    }
}
