package SistemaBrownieTest;

import SistemaBrownie.Exceptions.*;
import SistemaBrownie.Funcionalidade.SistemaVendasMap;
import SistemaBrownie.Modelo.Brownie;
import SistemaBrownie.Modelo.Combos;
import SistemaBrownie.Modelo.TipoBrownie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    private SistemaVendasMap sistema;
    private final String TEST_BROWNIE = "Chocolate";
    private final String TEST_COMBO = "Especial";

    @BeforeEach
    public void setUp() {
        sistema = new SistemaVendasMap();
    }

    @Test
    public void testCadastrarBrownie() {
        assertDoesNotThrow(() -> {
            sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        });
    }
    @Test
    public void testCadastrarCombo() {
        assertDoesNotThrow(() -> {
            sistema.cadastraCombos(TEST_COMBO, 25.0);
        });
    }

    @Test
    public void testPesquisarBrowniePorSabor() throws BrownieJaExisteException, BrownieNaoExisteException {
        sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        List<Brownie> resultado = sistema.pesquisaBrowniePorSabor(TEST_BROWNIE);
        assertFalse(resultado.isEmpty());
        assertEquals(TEST_BROWNIE, resultado.get(0).getSabor());
    }

    @Test
    public void testExisteSabor() throws BrownieJaExisteException {
        sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        assertTrue(sistema.existeSabor(TEST_BROWNIE));
        assertFalse(sistema.existeSabor("SaborInexistente"));
    }

    @Test
    public void testContaBrownieDoTipo() throws BrownieJaExisteException {
        sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        sistema.cadastrarBrownie("Morango", 15.0, TipoBrownie.RECHEADO);

        assertEquals(1, sistema.contaBrownieDoTipo(TipoBrownie.CLÁSSICO));
        assertEquals(1, sistema.contaBrownieDoTipo(TipoBrownie.RECHEADO));
        assertEquals(0, sistema.contaBrownieDoTipo(TipoBrownie.GOURMET));
    }

    @Test
    public void testRemoveSabor() throws BrownieJaExisteException, BrownieNaoExisteException {
        sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        sistema.removeSabor(TEST_BROWNIE);
        assertFalse(sistema.existeSabor(TEST_BROWNIE));
    }

    @Test
    public void testRemoveSaborInexistente() {
        assertThrows(BrownieNaoExisteException.class, () -> {
            sistema.removeSabor("SaborInexistente");
        });
    }

    @Test
    public void testRemoveCombo() throws ComboJaExisteException, ComboNaoExisteException {
        sistema.cadastraCombos(TEST_COMBO, 25.0);
        sistema.removeCombo(TEST_COMBO);
        assertTrue(sistema.pesquisaCombos(TEST_COMBO).isEmpty());
    }

    @Test
    public void testRemoveComboInexistente() {
        assertThrows(ComboNaoExisteException.class, () -> {
            sistema.removeCombo("ComboInexistente");
        });
    }

    @Test
    public void testPesquisaValoresPorFaixa() throws BrownieJaExisteException {
        sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        sistema.cadastrarBrownie("Caramelo", 8.0, TipoBrownie.CLÁSSICO);
        sistema.cadastrarBrownie("Ninho", 20.0, TipoBrownie.GOURMET);

        List<Brownie> resultado = sistema.pesquisaValoresPorFaixa(9.0, 16.0);
        assertEquals(1, resultado.size()); // Apenas Chocolate (10.0)
    }

    @Test
    public void testExisteBrownieDoTipo() throws BrownieJaExisteException {
        sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        assertTrue(sistema.existeBrownieDoTipo(TipoBrownie.CLÁSSICO));
        assertFalse(sistema.existeBrownieDoTipo(TipoBrownie.GOURMET));
    }

    @Test
    public void testPesquisaCombos() throws ComboJaExisteException {
        sistema.cadastraCombos(TEST_COMBO, 25.0);
        List<Combos> resultado = sistema.pesquisaCombos(TEST_COMBO);
        assertFalse(resultado.isEmpty());
        assertEquals(TEST_COMBO, resultado.get(0).getNome());
    }

    @Test
    public void testRecuperarDados() throws BrownieJaExisteException, ComboJaExisteException {
        sistema.cadastrarBrownie(TEST_BROWNIE, 10.0, TipoBrownie.CLÁSSICO);
        sistema.cadastraCombos(TEST_COMBO, 25.0);

        // Criar novo sistema que irá recuperar os dados
        SistemaVendasMap novoSistema = new SistemaVendasMap();

        // Verificar se os dados foram recuperados
        assertTrue(novoSistema.existeSabor(TEST_BROWNIE));
        assertFalse(novoSistema.pesquisaCombos(TEST_COMBO).isEmpty());
    }
}