package SistemaBrownie.Funcionalidade;

import SistemaBrownie.Exceptions.BrownieNaoExisteException;
import SistemaBrownie.Exceptions.ComboJaExisteException;
import SistemaBrownie.Exceptions.ComboNaoExisteException;
import SistemaBrownie.Exceptions.BrownieJaExisteException;
import SistemaBrownie.Interface.SistemaVendas;
import SistemaBrownie.Modelo.Brownie;
import SistemaBrownie.Modelo.Combos;
import SistemaBrownie.Modelo.TipoBrownie;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaVendasMap implements SistemaVendas {
    private Map<String, Brownie> brownies;
    private Map<String, Combos> combos;
    private GravadorDeDados gravador = new GravadorDeDados();

    public SistemaVendasMap() {
        this.brownies = new HashMap<>();
        this.combos = new HashMap<>();
        recuperarDadosBrownie();
        recuperarDadosCombos();
    }

    public void recuperarDadosBrownie() {
        try {
            this.brownies = this.gravador.recuperarBrownies();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void recuperarDadosCombos() {
        try {
            this.combos = this.gravador.recuperarCombos();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void cadastrarBrownie(String sabor, double preco, TipoBrownie tipo) throws BrownieJaExisteException {
        if (this.brownies.containsKey(sabor)) {
            throw new BrownieJaExisteException("Já existe este brownie cadastrado");
        } else {
            this.brownies.put(sabor, new Brownie(sabor, preco, tipo));
            try {
                this.gravador.salvarBrownies(this.brownies);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Brownie cadastrado com sucesso!");
        }
    }

    @Override
    public void cadastraCombos(String nome, double preco) throws ComboJaExisteException {
        if (this.combos.containsKey(nome)) {
            throw new ComboJaExisteException("Já existe este combo");
        } else {
            this.combos.put(nome, new Combos(nome, preco));
            try {
                this.gravador.salvarCombos(this.combos);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Combo cadastrado com sucesso!");
        }
    }

    //Usando streams
    @Override
    public List<Brownie> pesquisaBrowniePorSabor(String sabor) {
        return this.brownies.values().stream()
                .filter(c -> c.getSabor().equalsIgnoreCase(sabor))
                .collect(Collectors.toList());
    }


    @Override
    public boolean existeSabor(String sabor) {
        return this.brownies.containsKey(sabor);
    }

    //Usando streams
    @Override
    public int contaBrownieDoTipo(TipoBrownie tipo) {
        return (int) this.brownies.values().stream()
                .filter(c -> c.ehDoTipo(tipo))
                .count();
    }

    @Override
    public void removeSabor(String sabor) throws BrownieNaoExisteException {
        if (this.brownies.containsKey(sabor)) {
            this.brownies.remove(sabor);
            try {
                this.gravador.salvarBrownies(this.brownies);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Sabor pistache removido com sucesso!");
        } else {
            throw new BrownieNaoExisteException("Não foi encontrado este sabor  de pistache");
        }
    }

    public void removeCombo(String nome) throws ComboNaoExisteException {
        if (this.combos.containsKey(nome)) {
            this.combos.remove(nome);
            try {
                this.gravador.salvarCombos(this.combos);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Combo removido com sucesso!");
        } else {
            throw new ComboNaoExisteException("Não foi encontrado este combo");
        }
    }

    //Usando streams
    @Override
    public List<Brownie> pesquisaValoresPorFaixa(double valorMinimo, double valorMaximo) {
        return this.brownies.values().stream()
                .filter(c -> c.getPreco() >= valorMinimo && c.getPreco() <= valorMaximo)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeBrownieDoTipo(TipoBrownie tipo) {
        for (Brownie c : this.brownies.values()) {
            if (c.ehDoTipo(tipo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Combos> pesquisaCombos(String combo) {
        try {
            this.combos = this.gravador.recuperarCombos();
            System.out.println("Recuperando combos: " + this.combos.size());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        List<Combos> list = new ArrayList<>();
        for (Combos c : this.combos.values()) {
            if (c.getNome().equalsIgnoreCase(combo)) {
                list.add(c);
            }
        }
        System.out.println("Combos encontrados: " + list.size());
        return list;
    }
}
