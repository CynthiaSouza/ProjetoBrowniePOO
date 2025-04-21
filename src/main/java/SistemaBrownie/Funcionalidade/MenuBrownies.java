
package SistemaBrownie.Funcionalidade;

import SistemaBrownie.Exceptions.BrownieJaExisteException;
import SistemaBrownie.Exceptions.BrownieNaoExisteException;
import SistemaBrownie.Exceptions.ComboJaExisteException;
import SistemaBrownie.Exceptions.ComboNaoExisteException;
import SistemaBrownie.Interface.SistemaVendas;
import SistemaBrownie.Modelo.Brownie;
import SistemaBrownie.Modelo.TipoBrownie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuBrownies extends JFrame {
    private JFrame frame;
    private SistemaVendas sistema;

    public MenuBrownies() {
        sistema = new SistemaVendasMap();

        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        frame = new JFrame("Sistema Gerenciador de Brownies");

        JLabel imagemLabel = new JLabel(icon);
        imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(imagemLabel, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastra = createMenu("Cadastrar", new String[]{"Cadastrar brownie"}, this::cadastraItemAction);
        JMenu menuPesquisa = createMenu("Pesquisar", new String[]{"Pesquisar brownie por sabor", "Pesquisar por faixa de preço"}, this::pesquisaItemAction);
        JMenu menuConta = createMenu("Estoque", new String[]{"Quantidade de brownie do tipo"}, this::contaItemAction);
        JMenu menuRemove = createMenu("Remover", new String[]{"Remover sabor"}, this::removeItemAction);
        JMenu menuExiste = createMenu("Existe", new String[]{"Existe brownie do tipo", "Existe brownie com sabor"}, this::existeItemAction);

        menuBar.add(menuCadastra);
        menuBar.add(menuPesquisa);
        menuBar.add(menuConta);
        menuBar.add(menuRemove);
        menuBar.add(menuExiste);

        return menuBar;
    }

    private JMenu createMenu(String name, String[] items, ActionListener listener) {
        JMenu menu = new JMenu(name);
        for (String item : items) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(listener);
            menu.add(menuItem);
        }
        return menu;
    }

    private void cadastraItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        if (source.getText().equals("Cadastrar brownie")) {
            cadastraBrownie();
        }
    }

    private void cadastraBrownie() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor do brownie: "));

        JTextField saborField = new JTextField(10);
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        panel.add(new JLabel("Digite o preço do brownie: "));
        JTextField precoField = new JTextField(10);
        precoField.setMaximumSize(new Dimension(200, 20));
        panel.add(precoField);

        panel.add(new JLabel("Escolha o tipo do brownie: "));
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "TEMÁTICO", "ALCOÓLICO"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);
        tipoBox.setMaximumSize(new Dimension(200, 20));
        panel.add(tipoBox);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Cadastrar Brownie");
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            String tipo = (String) tipoBox.getSelectedItem();
            try {
                double preco = Double.parseDouble(precoField.getText());
                sistema.cadastrarBrownie(sabor, preco, TipoBrownie.valueOf(tipo.toUpperCase()));
                JOptionPane.showMessageDialog(frame, "Brownie cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (BrownieJaExisteException ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Brownie já existe!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void pesquisaItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        String acao = source.getText();
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));

        if (acao.equals("Pesquisar brownie por sabor")) {
            String sabor = JOptionPane.showInputDialog(frame, "Digite o sabor do brownie:", "Pesquisar por Sabor", JOptionPane.QUESTION_MESSAGE);
            List<Brownie> resultado = null;
            try {
                resultado = sistema.pesquisaBrowniePorSabor(sabor);
            } catch (BrownieNaoExisteException ex) {
                throw new RuntimeException(ex);
            }
            if (!resultado.isEmpty()) {
                resultado.forEach(b -> JOptionPane.showMessageDialog(frame, b.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE, icon));
            } else {
                JOptionPane.showMessageDialog(frame, "Nenhum brownie encontrado com esse sabor.", "Resultado", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        } else if (acao.equals("Pesquisar por faixa de preço")) {
            String min = JOptionPane.showInputDialog(frame, "Digite valor mínimo:");
            String max = JOptionPane.showInputDialog(frame, "Digite valor máximo:");
            try {
                double minVal = Double.parseDouble(min);
                double maxVal = Double.parseDouble(max);
                List<Brownie> resultado = sistema.pesquisaValoresPorFaixa(minVal, maxVal);
                if (!resultado.isEmpty()) {
                    resultado.forEach(b -> JOptionPane.showMessageDialog(frame, b.toString(), "Resultado", JOptionPane.INFORMATION_MESSAGE, icon));
                } else {
                    JOptionPane.showMessageDialog(frame, "Nenhum brownie na faixa especificada.", "Resultado", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Digite valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void contaItemAction(ActionEvent e) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "TEMÁTICO", "ALCOÓLICO"};
        String tipo = (String) JOptionPane.showInputDialog(frame, "Tipo:", "Contar Brownies", JOptionPane.QUESTION_MESSAGE, icon, tipos, tipos[0]);
        int quantidade = sistema.contaBrownieDoTipo(TipoBrownie.valueOf(tipo));
        JOptionPane.showMessageDialog(frame, "Quantidade: " + quantidade, "Resultado", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private void removeItemAction(ActionEvent e) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        String sabor = JOptionPane.showInputDialog(frame, "Digite o sabor para remover:");
        try {
            sistema.removeSabor(sabor);
            JOptionPane.showMessageDialog(frame, "Brownie removido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (BrownieNaoExisteException ex) {
            JOptionPane.showMessageDialog(frame, "Brownie não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void existeItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));

        if (source.getText().equals("Existe brownie do tipo")) {
            String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "TEMÁTICO", "ALCOÓLICO"};
            String tipo = (String) JOptionPane.showInputDialog(frame, "Tipo:", "Verificar Tipo", JOptionPane.QUESTION_MESSAGE, icon, tipos, tipos[0]);
            boolean existe = sistema.existeBrownieDoTipo(TipoBrownie.valueOf(tipo));
            JOptionPane.showMessageDialog(frame, existe ? "Existe!" : "Não existe.", "Resultado", JOptionPane.INFORMATION_MESSAGE, icon);
        } else {
            String sabor = JOptionPane.showInputDialog(frame, "Sabor:", "Verificar Sabor", JOptionPane.QUESTION_MESSAGE);
            boolean existe = sistema.existeSabor(sabor);
            JOptionPane.showMessageDialog(frame, existe ? "Existe!" : "Não existe.", "Resultado", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    public static void main(String[] args) {
        new MenuBrownies();
    }
}
