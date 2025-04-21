package SistemaBrownie.Interface;//import Modelo.*;

import SistemaBrownie.Exceptions.BrownieNaoExisteException;
import SistemaBrownie.Exceptions.ComboJaExisteException;
import SistemaBrownie.Exceptions.ComboNaoExisteException;
import SistemaBrownie.Modelo.Brownie;
import SistemaBrownie.Exceptions.BrownieJaExisteException;
import SistemaBrownie.Modelo.Combos;
import SistemaBrownie.Modelo.TipoBrownie;

import java.util.List;

public interface SistemaVendas {

    List<Combos> pesquisaCombos(String combo);

    void cadastrarBrownie(String sabor, double preco, TipoBrownie tipo) throws BrownieJaExisteException;

    void cadastraCombos(String nome, double preco) throws ComboJaExisteException;

    List<Brownie> pesquisaBrowniePorSabor(String sabor) throws BrownieNaoExisteException;

    boolean existeSabor(String sabor);

    int contaBrownieDoTipo(TipoBrownie tipo);

    void removeSabor(String sabor) throws BrownieNaoExisteException;

    void removeCombo(String combo) throws ComboNaoExisteException;

    List<Brownie> pesquisaValoresPorFaixa(double valorMinimo, double valorMaximo);

    boolean existeBrownieDoTipo(TipoBrownie tipo);

    void recuperarDadosBrownie();

    void recuperarDadosCombos();
}
