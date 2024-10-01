import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Jogo implements Serializable{
    private int numTentativas;
    private Palavra palavra;
    //    private Forca forca;
    private int numVitorias;
    private Boolean jogoSalvo = false;
    private int numDerrotas;
    private int numAcertos;
    private ArrayList<Character> letrasEscolhidas;
    private String nomeJogador;

    private static final long serialVersionUID = 1L;

    public Jogo() {

//        forca = new Forca();
        letrasEscolhidas = new ArrayList<>();
    }

    public Jogo(int numTentativas, Palavra palavra, int numVitorias, int numDerrotas, int numAcertos, ArrayList<Character> letrasEscolhidas) {
        this.numTentativas = numTentativas;
        this.palavra = palavra;
//        this.forca = forca;
        this.numVitorias = numVitorias;
        this.numDerrotas = numDerrotas;
        this.numAcertos = numAcertos;
        this.letrasEscolhidas = letrasEscolhidas;
    }

    public Boolean getJogoSalvo(){return jogoSalvo;}

    public void setJogoSalvo(Boolean jogoSalvo){this.jogoSalvo = jogoSalvo;}

    public int getNumTentativas() {
        return numTentativas;
    }

    public void setNumTentativas(int numTentativas) {
        this.numTentativas = numTentativas;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }

//    public Forca getForca() {s
//        return forca;
//    }

//    public void setForca(Forca forca) {
//        this.forca = forca;
//    }

    public int getNumVitorias() {
        return numVitorias;
    }

    public void setNumVitorias(int numVitorias) {
        this.numVitorias = numVitorias;
    }

    public int getNumAcertos() {
        return numAcertos;
    }

    public void setNumAcertos(int numAcertos) {
        this.numAcertos = numAcertos;
    }

    public int getNumDerrotas() {
        return numDerrotas;
    }

    public void setNumDerrotas(int numDerrotas) {
        this.numDerrotas = numDerrotas;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public ArrayList<Character> getLetrasEscolhidas() {
        return letrasEscolhidas;
    }

    public void setLetrasEscolhidas(ArrayList<Character> letrasEscolhidas) {
        this.letrasEscolhidas = letrasEscolhidas;
    }

    public void iniciarNovoJogo(){
        palavra = new Palavra("MELANCIA", "Frutas");
        setNumTentativas(6);
        letrasEscolhidas.clear();
        numAcertos = 0;
    }

    public String getLetrasEscolhidasFormatadas() {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < letrasEscolhidas.size(); i++) {
            formatted.append(String.format("%c", letrasEscolhidas.get(i)));
            if (i < letrasEscolhidas.size() - 1) {
                formatted.append(", ");
            }
        }
        return formatted.toString();
    }

    public boolean adivinharLetra(char letra){
        boolean achou = false;
        for(int i = 0; i < palavra.getPalavra().length(); i++){
            if(palavra.getPalavra().charAt(i) == letra){
                palavra.getLetrasAdvinhadas().set(i, letra);
                numAcertos++;
                achou = true;
            }
        }
        return achou;
    }

    public void salvarJogo(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Serialize game state
        	oos.writeObject(nomeJogador);
            oos.writeInt(numTentativas);
            oos.writeObject(palavra);
//            oos.writeObject(forca);
            oos.writeInt(numVitorias);
            oos.writeInt(numDerrotas);
            oos.writeInt(numAcertos);
            oos.writeObject(letrasEscolhidas);
            System.out.println("Jogo salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }


    public void carregarJogo(String filename) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            // Deserialize the game state (example)
        	nomeJogador = (String) ois.readObject();
            numTentativas = ois.readInt();
            palavra = (Palavra) ois.readObject();
//            forca = (Forca) ois.readObject();
            numVitorias = ois.readInt();
            numDerrotas = ois.readInt();
            numAcertos = ois.readInt();
            letrasEscolhidas = (ArrayList<Character>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Handle file not found logic internally
            System.out.println("Nenhum jogo salvo encontrado. Iniciando um novo jogo.");
            iniciarNovoJogo(); // Start a new game if the file is not found
            return; // Exit the method if a new game is started
        } catch (IOException e) {
            // Rethrow other I/O exceptions
            throw e;
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao carregar o jogo: classe não encontrada.", e);
        }
    }
}





