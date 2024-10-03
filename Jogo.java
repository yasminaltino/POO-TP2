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

    private boolean versus;
    private int statusVersus;
    public enum NivelDificuldade {FACIL, MEDIO, DIFICIL, VERSUS};
    private NivelDificuldade nivelDificuldade;

    private String[] frutasFacil = {"Laranja", "Melancia", "Abacaxi", "Goiaba", "Banana"};
    private String[] frutasMedio = {"Carambola", "Acerola", "Amora", "Pitanga", "Lichia"};
    private String[] frutasDificil = {"Lichia", "Pitaya", "Siriguela", "Pequi", "Jenipapo"};
    private String[] coresFacil = {"Vermelho", "Amarelo", "Laranja", "Cinza", "Branco"};
    private String[] coresMedio = {"Violeta", "Dourado", "Cobre", "Esmeralda", "Caramelo"};
    private String[] coresDificil = {"Magenta", "Ciano", "Fucsia", "Ocre", "Terracota"};
    private String[] paisesFacil = {"Brasil", "Canada", "Mexico", "Franca", "China"};
    private String[] paisesMedio = {"Dinamarca", "Finlandia", "Equador", "Taiwan", "Honduras"};
    private String[] paisesDificil = {"Azerbaijao", "Kosovo", "Bangladesh", "Singapura", "Eslovaquia"};

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
    
    public boolean isVersus() {
        return versus;
    }

    public void setVersus(boolean versus) {
        this.versus = versus;
    }

    public int getStatusVersus() {
        return statusVersus;
    }

    public void setStatusVersus(int statusVersus) {
        this.statusVersus = statusVersus;
    }

    public NivelDificuldade getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade(int dificuldade) {
        if(dificuldade == 1){
            this.nivelDificuldade = NivelDificuldade.FACIL;
        }else if(dificuldade == 1){
            this.nivelDificuldade = NivelDificuldade.MEDIO;
        }else if(dificuldade == 2){
            this.nivelDificuldade = NivelDificuldade.DIFICIL;
        }else{
            this.nivelDificuldade = NivelDificuldade.VERSUS;
        }
    }

    public ArrayList<Character> getLetrasEscolhidas() {
        return letrasEscolhidas;
    }

    public void setLetrasEscolhidas(ArrayList<Character> letrasEscolhidas) {
        this.letrasEscolhidas = letrasEscolhidas;
    }

    public void iniciarNovoJogo(){
        sortearPalavra();
        setNumTentativas(6);
        letrasEscolhidas.clear();
        numAcertos = 0;
    }

    public void sortearPalavra() {
        Random rand = new Random();
        String tema = "Error";
        String stringPalavra = "Error";
        int numTema = rand.nextInt(3);
        int numPalavra = rand.nextInt(5);

        System.out.println("Tema: " + numTema + " Palavra: " + numPalavra);


        switch (numTema) {
            case 0:
                tema = "Frutas";
                if (nivelDificuldade == NivelDificuldade.FACIL) {
                    stringPalavra = frutasFacil[numPalavra].toUpperCase();
                } else if (nivelDificuldade == NivelDificuldade.MEDIO) {
                    stringPalavra = frutasMedio[numPalavra].toUpperCase();
                } else {
                    stringPalavra = frutasDificil[numPalavra].toUpperCase();
                }
                break;
            case 1:
                tema = "Cores";
                if (nivelDificuldade == NivelDificuldade.FACIL) {
                    stringPalavra = coresFacil[numPalavra].toUpperCase();
                } else if (nivelDificuldade == NivelDificuldade.MEDIO) {
                    stringPalavra = coresMedio[numPalavra].toUpperCase();
                } else {
                    stringPalavra = coresDificil[numPalavra].toUpperCase();
                }
                break;
            case 2:
                tema = "País";
                if (nivelDificuldade == NivelDificuldade.FACIL) {
                    stringPalavra = paisesFacil[numPalavra].toUpperCase();
                } else if (nivelDificuldade == NivelDificuldade.MEDIO) {
                    stringPalavra = paisesMedio[numPalavra].toUpperCase();
                } else {
                    stringPalavra = paisesDificil[numPalavra].toUpperCase();

                }
                break;


        }
        palavra = new Palavra(stringPalavra, tema);
    }

    public String getLetrasEscolhidasFormatadas() {
    	
    	// Instancia objeto StringBuilder para auxiliar na construção da String de retorno do método
        StringBuilder formatted = new StringBuilder();
         
        for (int i = 0; i < letrasEscolhidas.size(); i++) {
        	
        	// Insere o caractere de indice i
            formatted.append(String.format("%c", letrasEscolhidas.get(i)));
            
         // Insere ", " em seguida do caractere, exceto do último, para separá-los devidamente
            if (i < letrasEscolhidas.size() - 1)
                formatted.append(", ");
            
        }
        
        return formatted.toString();
    }

    public boolean adivinharLetra(char letra){
    //  Variável que controla o retorno da função
    	boolean achou = false;
        
     // Percorre a String procurando as incidencias da letra escolhida pelo jogador
        for(int i = 0; i < palavra.getPalavra().length(); i++){
        	
            if(palavra.getPalavra().charAt(i) == letra){
            	
            // Quando encontrada uma incidencia da letra, revela-a e atualiza o número de acertos 
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
            
        } 
        
        catch (IOException e) {
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
            
        }
        
        catch (FileNotFoundException e) {
        	
            // Handle file not found logic internally
            System.out.println("Nenhum jogo salvo encontrado. Iniciando um novo jogo.");
            
            iniciarNovoJogo(); // Start a new game if the file is not found
            
            return; // Exit the method if a new game is started
            
        }
        
        catch (IOException e) {
        	
            // Rethrow other I/O exceptions
            throw e;
            
        }
        
        catch (ClassNotFoundException e) {
        	
            throw new IOException("Erro ao carregar o jogo: classe não encontrada.", e);
            
        }
    }
}





