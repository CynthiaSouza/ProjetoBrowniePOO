package SistemaBrownie.Modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Combos implements Serializable {
    private String nome;
    private List<Brownie> sabor;
    private double preco;

    public Combos(String nome, double preco){
        this.nome = nome;
        this.preco = preco;
    }
    public Combos(String nome, List<Brownie> sabor, double preco){
        this.nome = nome;
        this.sabor = sabor;
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combos combos = (Combos) o;
        return Objects.equals(nome, combos.nome) && Objects.equals(sabor, combos.sabor) && Objects.equals(preco, combos.preco);
    }
    @Override
    public int hashCode(){
        return Objects.hash(nome, sabor, preco);
    }

    @Override
    public String toString(){
        return "Modelo.Combos(" +"nome='" + nome + '\'' +", sabor=" + sabor + ", preco=" + preco +')';
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public List<Brownie> getSabor(){
        return sabor;
    }

    public void setSabor(List<Brownie> sabor){
        this.sabor = sabor;
    }

    public double getPreco(){
        return preco;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }
}
