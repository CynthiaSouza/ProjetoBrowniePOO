package SistemaBrownie.Funcionalidade;

import SistemaBrownie.Modelo.Brownie;
import SistemaBrownie.Modelo.Combos;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GravadorDeDados {
    public static final String ARQUIVO_DE_BROWNIE = "brownie.dat";
    public static final String ARQUIVO_DE_COMBOS = "combos.dat";


    public HashMap<String, Brownie> recuperarBrownies() throws IOException{
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream(ARQUIVO_DE_BROWNIE));
            return (HashMap<String, Brownie>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de brownies não encontrado. Iniciando com lista vazia.");
            return new HashMap<>();
        } catch (Exception e) {
            System.out.println("Não foi possível recuperar os dados dos brownies");
            throw new IOException("Não foi possível recuperar os dados do arquivo " + ARQUIVO_DE_BROWNIE);
        } finally {
            if (in!=null){
                in.close();
            }
        }
    }

    public HashMap<String, Combos> recuperarCombos() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(ARQUIVO_DE_COMBOS));
            return (HashMap<String, Combos>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de combos não encontrado. Iniciando com lista vazia.");
            return new HashMap<>(); // Retorna um HashMap vazio se o arquivo não for encontrado
        } catch (Exception e) {
            System.out.println("Não foi possível recuperar os dados dos combos");
            throw new IOException("Não foi possível recuperar os dados do arquivo " + ARQUIVO_DE_COMBOS);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void salvarBrownies(Map<String, Brownie> brownies) throws IOException{
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DE_BROWNIE));
            out.writeObject(brownies);
        } catch (Exception e){
            e.printStackTrace();
            throw new IOException("Erro ao salvar os brownies no arquivo " + ARQUIVO_DE_BROWNIE);
        } finally {
            if (out != null){
                out.close();
            }
        }
    }

    public void salvarCombos(Map<String, Combos> combos) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DE_COMBOS));
            out.writeObject(combos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro ao salvar os combos no arquivo " + ARQUIVO_DE_COMBOS);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
