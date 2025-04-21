package Funcionalidade;

import Exceptions.BrownieJaExisteException;
import Exceptions.BrownieNaoExisteException;
import Exceptions.ComboJaExisteException;
import Exceptions.ComboNaoExisteException;
import Interface.SistemaVendas;
import Modelo.Brownie;
import Modelo.TipoBrownie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class MenuBrownies extends JFrame {
    private JFrame frame;
    private SistemaVendas sistema;

    public MenuBrownies() {
        sistema = new SistemaVendasMap();

        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        frame = new JFrame("Sistema Gerenciador de Brownies");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastra = createMenu("Cadastrar", new String[]{"Cadastrar brownie", "Cadastrar combos"}, this::cadastraItemAction);
        JMenu menuPesquisa = createMenu("Pesquisar", new String[]{"Pesquisar brownie por sabor", "Pesquisar por faixa de preço"}, this::pesquisaItemAction);
        JMenu menuConta = createMenu("Estoque", new String[]{"Quantidade de brownie do tipo"}, this::contaItemAction);
        JMenu menuRemove = createMenu("Remover", new String[]{"Remover sabor", "Remover combo"}, this::removeItemAction);
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

        switch (source.getText()) {
            case "Cadastrar brownie":
                cadastraBrownie();
                break;
            case "Cadastrar combos":
                cadastraCombo();
                break;
        }
    }

    private void cadastraBrownie() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor do brownie: "));

        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        panel.add(new JLabel("Digite o preço do brownie: "));
        JTextField precoField = new JTextField(10);
        precoField.setPreferredSize(new Dimension(200, 20));
        precoField.setMaximumSize(new Dimension(200, 20));
        panel.add(precoField);

        panel.add(new JLabel("Escolha o tipo do brownie: "));
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "PERSONALIZADO", "ESPECIAL"};
        JComboBox<String> tipoBox = new JComboBox<>(tipos);
        tipoBox.setMaximumSize(new Dimension(200, 20));
        panel.add(tipoBox);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Cadastrar Brownie");
        dialog.pack(); // Ajusta o tamanho da janela para caber os componentes
        dialog.setLocationRelativeTo(frame); // Centraliza a janela em relação ao frame
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            String tipo = (String) tipoBox.getSelectedItem();
            try {
                double precoStr = Double.parseDouble(precoField.getText());
                sistema.cadastrarBrownie(sabor, precoStr, TipoBrownie.valueOf(tipo.toUpperCase()));
                JOptionPane.showMessageDialog(frame, "Brownie cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (BrownieJaExisteException ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Brownie já existe!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void cadastraCombo() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o nome do combo:"));
        JTextField nomeField = new JTextField(10);
        nomeField.setPreferredSize(new Dimension(200, 20));
        nomeField.setMaximumSize(new Dimension(200, 20));
        panel.add(nomeField);
        panel.add(new JLabel("Digite o preço do combo:"));
        JTextField precoField = new JTextField(10);
        precoField.setPreferredSize(new Dimension(200, 20));
        precoField.setMaximumSize(new Dimension(200, 20));
        panel.add(precoField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Cadastrar Combo");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            double preco = Double.parseDouble(precoField.getText());
            try {
                sistema.cadastrarCombos(nome, preco);
                JOptionPane.showMessageDialog(frame, "Combo cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (ComboJaExisteException ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Combo já existe.", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void pesquisaItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case "Pesquisar brownie por sabor":
                pesquisaBrowniePorSabor();
                break;
            case "Pesquisar por faixa de preço":
                pesquisaPorFaixaDePreco();
                break;
        }
    }
    private void pesquisaBrowniePorSabor() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor para pesquisar:"));
        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Pesquisar Brownie por Sabor");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            try {
            List<Brownie> lista = new ArrayList<>(sistema.pesquisaBrowniePorSabor(sabor));
            lista.forEach(c -> JOptionPane.showMessageDialog(frame, c.toString(), "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE, icon));
        } catch (BrownieJaExisteException ex) {
            JOptionPane.showMessageDialog(frame, "Erro: Brownie não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            } catch (BrownieNaoExisteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void pesquisaPorFaixaDePreco() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o valor mínimo: "));

        JTextField minimoField = new JTextField(5);
        minimoField.setPreferredSize(new Dimension(200, 20));
        minimoField.setMaximumSize(new Dimension(200, 20));
        panel.add(minimoField);

        panel.add(new JLabel("Digite o valor máximo: "));

        JTextField maximoField = new JTextField(5);
        maximoField.setPreferredSize(new Dimension(200, 20));
        maximoField.setMaximumSize(new Dimension(200, 20));
        panel.add(maximoField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Pesquisar por Faixa de Preço");
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            try {
                double minimo = Double.parseDouble(minimoField.getText());
                double maximo = Double.parseDouble(maximoField.getText());
                List<Brownie> lista = sistema.pesquisaValoresPorFaixa(minimo, maximo);
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nenhum brownie encontrado nessa faixa de preço.", "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE, icon);
                } else {
                    StringBuilder resultado = new StringBuilder();
                    for (Brownie c : lista) {
                        resultado.append(c.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, resultado.toString(), "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void contaItemAction(ActionEvent e) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "PERSONALIZADO", "ESPECIAL"};
        String tipoStr = (String) JOptionPane.showInputDialog(frame, "Escolha o tipo do brownie:", "Contar Brownie do Tipo",
                JOptionPane.QUESTION_MESSAGE, icon, tipos, tipos[0]);
        JOptionPane.showMessageDialog(frame, sistema.contaBrowniesDoTipo(TipoBrownie.valueOf(tipoStr)), "Resultado da Contagem", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private void removeItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case "Remover sabor":
                removeSabor();
                break;
            case "Remover combo":
                removeCombo();
                break;
        }
    }

    private void removeSabor() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor para remover:"));

        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Remover Sabor");
        dialog.setLocationRelativeTo(frame);
//        dialog.setSize(900, 700);
//        dialog.setLocation(300, 200);
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            try {
                sistema.removeSabor(sabor);
                JOptionPane.showMessageDialog(frame, "Brownie removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (BrownieNaoExisteException e) {
                JOptionPane.showMessageDialog(frame, "Erro: Sabor não encontrado. ", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void removeCombo() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o combo para remover: "));
        JTextField comboField = new JTextField(10);
        comboField.setPreferredSize(new Dimension(200, 20));
        comboField.setMaximumSize(new Dimension(200, 20));
        panel.add(comboField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Remover Combo");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String combo = comboField.getText();
            try {
                sistema.removeCombo(combo);
                JOptionPane.showMessageDialog(frame, "Combo removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, icon);
            } catch (ComboNaoExisteException ex) {
                JOptionPane.showMessageDialog(frame, "Erro: Combo não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    private void existeItemAction(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case "Existe brownie do tipo":
                existeBrownieDoTipo();
                break;
            case "Existe brownie com sabor":
                existeBrownieComSabor();
                break;
        }
    }

    private void existeBrownieDoTipo() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        String[] tipos = {"CLÁSSICO", "RECHEADO", "GOURMET", "PERSONALIZADO", "ESPECIAL"};
        String tipoStr = (String) JOptionPane.showInputDialog(frame, "Escolha o tipo do brownie: ", "Existe brownie do tipo",
                JOptionPane.QUESTION_MESSAGE, icon, tipos, tipos[0]);
        JOptionPane.showMessageDialog(frame, sistema.existeBrowniesDoTipo(TipoBrownie.valueOf(tipoStr)), "Resultado da Verificação", JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private void existeBrownieComSabor() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/brownie.jpg"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Digite o sabor do brownie:"));
        JTextField saborField = new JTextField(10);
        saborField.setPreferredSize(new Dimension(200, 20));
        saborField.setMaximumSize(new Dimension(200, 20));
        panel.add(saborField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, icon);
        JDialog dialog = optionPane.createDialog(frame, "Existe brownie com sabor");
        dialog.setVisible(true);

        int result = (Integer) optionPane.getValue();
        if (result == JOptionPane.OK_OPTION) {
            String sabor = saborField.getText();
            JOptionPane.showMessageDialog(frame, sistema.existeSabor(sabor), "Resultado da Verificação", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    public static void main(String[] args) {
        SistemaVendasMap cardapio = new SistemaVendasMap();
        MenuBrownies menu = new MenuBrownies();
    }
}