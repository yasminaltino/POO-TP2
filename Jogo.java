//Importacao de diferentes classes e pacotes que serao utilizados no codigo
import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Random;

public class Jogo implements Serializable{
//atributos da classe Jogo
    private int numTentativas;
    private Palavra palavra;
    private int numVitorias;
    private boolean jogoSalvo = false;
    private int numDerrotas;
    private int numAcertos;
    private ArrayList<Character> letrasEscolhidas;
    private String nomeJogador;
    private boolean versus;
    private int statusVersus;
    public enum NivelDificuldade {FACIL, MEDIO, DIFICIL, VERSUS;};
    private NivelDificuldade nivelDificuldade;

    private ArrayList<String> palavrasCorretas; //////////////

    //Diferentes palavras disponiveis para serem descobertas no jogo
    private static String[] frutasFacil = {"Laranja", "Melancia", "Abacaxi", "Goiaba", "Banana"};
    private static String[] frutasMedio = {"Carambola", "Acerola", "Amora", "Pitanga", "Lichia"};
    private static String[] frutasDificil = {"Lichia", "Pitaya", "Siriguela", "Pequi", "Jenipapo"};
    private static String[] coresFacil = {"Vermelho", "Amarelo", "Laranja", "Cinza", "Branco"};
    private static String[] coresMedio = {"Violeta", "Dourado", "Cobre", "Esmeralda", "Caramelo"};
    private static String[] coresDificil = {"Magenta", "Ciano", "Fucsia", "Ocre", "Terracota"};
    private static String[] paisesFacil = {"Brasil", "Canada", "Mexico", "Franca", "China"};
    private static String[] paisesMedio = {"Dinamarca", "Finlandia", "Equador", "Taiwan", "Honduras"};
    private static String[] paisesDificil = {"Azerbaijao", "Kosovo", "Bangladesh", "Singapura", "Eslovaquia"};

//    private static final long serialVersionUID = 1L;

//Construtores da classe Jogo
    public Jogo() {letrasEscolhidas = new ArrayList<>(); palavra = new Palavra(); palavrasCorretas = new ArrayList<>();}

    public Jogo(int numTentativas, Palavra palavra, int numVitorias, int numDerrotas, int numAcertos, ArrayList<Character> letrasEscolhidas) {
        this.numTentativas = numTentativas;
        this.palavra = palavra;
        this.numVitorias = numVitorias;
        this.numDerrotas = numDerrotas;
        this.numAcertos = numAcertos;
        this.letrasEscolhidas = letrasEscolhidas;
    }

    // Método para adicionar uma palavra correta
    public void adicionarPalavraCorreta(String palavra) {
        palavrasCorretas.add(palavra);
    }

    // Método para obter as palavras corretas
    public ArrayList<String> getPalavrasCorretas() {
        return palavrasCorretas;
    }

//Getters e Setters para os atributos privados da classe Jogo

    public Boolean getJogoSalvo(){return jogoSalvo;}
    public void setJogoSalvo(Boolean jogoSalvo){this.jogoSalvo = jogoSalvo;}
    public int getNumTentativas() {return numTentativas;}
    public void setNumTentativas(int numTentativas) {this.numTentativas = numTentativas;}
    public Palavra getPalavra() {return palavra;}
    public void setPalavra(Palavra palavra) {this.palavra = palavra;}
    public int getNumVitorias() {return numVitorias;}
    public void setNumVitorias(int numVitorias) {this.numVitorias = numVitorias;}
    public int getNumAcertos() {return numAcertos;}
    public void setNumAcertos(int numAcertos) {this.numAcertos = numAcertos;}
    public int getNumDerrotas() {return numDerrotas;}
    public void setNumDerrotas(int numDerrotas) {this.numDerrotas = numDerrotas;}
    public String getNomeJogador() {return nomeJogador;}
    public void setNomeJogador(String nomeJogador) {this.nomeJogador = nomeJogador;}
    public boolean isVersus() {return versus;}
    public void setVersus(boolean versus) {this.versus = versus;}
    public int getStatusVersus() {return statusVersus;}
    public void setStatusVersus(int statusVersus) {this.statusVersus = statusVersus;}
    public NivelDificuldade getNivelDificuldade() {return nivelDificuldade;}
    public ArrayList<Character> getLetrasEscolhidas() {return letrasEscolhidas;}
    public void setLetrasEscolhidas(ArrayList<Character> letrasEscolhidas) {this.letrasEscolhidas = letrasEscolhidas;}
    public void setNivelDificuldade(int dificuldade) {
        if(dificuldade == 1){
            this.nivelDificuldade = NivelDificuldade.FACIL;
        }else if(dificuldade == 2){
            this.nivelDificuldade = NivelDificuldade.MEDIO;
        }else if(dificuldade == 3){
            this.nivelDificuldade = NivelDificuldade.DIFICIL;
        }else{
            this.nivelDificuldade = NivelDificuldade.VERSUS;
        }
    }

//Metodo que inicializa a palavra a ser adivinhada e as tentativas do jogador quando o jogo eh iniciado
    public void iniciarNovoJogo(){
        sortearPalavra();
        letrasEscolhidas.clear();
        numAcertos = 0;
    }

//Metodo que reinicia a quantidade de acertos e o vetor de letras escolhidas pelo jogador
    public void resetarDados(){
        letrasEscolhidas.clear();
        numAcertos = 0;
    }

//Metodo responsavel por sortear a palavra que sera utilizada no jogo, das palavras disponiveis no banco de dados
    public void sortearPalavra() {
        Random rand = new Random();
        String tema = "Error";
        String stringPalavra = "Error";
        int numTema = rand.nextInt(3);
        int numPalavra = rand.nextInt(5);

        //As palavras sao escolhidas com base num tema e na dificuldade

        System.out.println("Tema: " + numTema + " Palavra: " + numPalavra);

        switch (numTema) {
            case 0:
                tema = "Frutas";
                if (nivelDificuldade == NivelDificuldade.FACIL) {
                    stringPalavra = frutasFacil[numPalavra].toUpperCase();
                    setNumTentativas(8);
                } else if (nivelDificuldade == NivelDificuldade.MEDIO) {
                    stringPalavra = frutasMedio[numPalavra].toUpperCase();
                    setNumTentativas(7);
                } else {
                    stringPalavra = frutasDificil[numPalavra].toUpperCase();
                    setNumTentativas(6);
                }
                break;
            case 1:
                tema = "Cores";
                if (nivelDificuldade == NivelDificuldade.FACIL) {
                    stringPalavra = coresFacil[numPalavra].toUpperCase();
                    setNumTentativas(8);
                } else if (nivelDificuldade == NivelDificuldade.MEDIO) {
                    stringPalavra = coresMedio[numPalavra].toUpperCase();
                    setNumTentativas(7);
                } else {
                    stringPalavra = coresDificil[numPalavra].toUpperCase();
                    setNumTentativas(6);
                }
                break;
            case 2:
                tema = "País";
                if (nivelDificuldade == NivelDificuldade.FACIL) {
                    stringPalavra = paisesFacil[numPalavra].toUpperCase();
                    setNumTentativas(8);
                } else if (nivelDificuldade == NivelDificuldade.MEDIO) {
                    stringPalavra = paisesMedio[numPalavra].toUpperCase();
                    setNumTentativas(7);
                } else {
                    stringPalavra = paisesDificil[numPalavra].toUpperCase();
                    setNumTentativas(6);

                }
                break;
        }
        palavra = new Palavra(stringPalavra, tema);
    }

    //Metodo que obtem as letras que o jogador ja utilizou durante o jogo
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

//Metodo que verifica se a letra inserida pelo jogador esta presente na palavra a ser adivinhada
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

//Metodo responsavel por salvar um jogo em um arquivo, podendo entao ser continuado futuramente
    public void salvarJogo(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            // Serializacao do estado do jogo
            // Passamos todas as variaveis necessarias para dentro do arquivo
        	oos.writeObject(nomeJogador);
            oos.writeInt(numTentativas);
            oos.writeObject(palavra);
            oos.writeInt(numVitorias);
            oos.writeInt(numDerrotas);
            oos.writeInt(numAcertos);
            oos.writeObject(letrasEscolhidas);
            
            System.out.println("Jogo salvo com sucesso.");
        }
    //Catch para quando nao eh possivel salvar o jogo
        catch (IOException e) {
            System.out.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

//Metodo responsavel por carregar um jogo ja salvo em um arquivo, dando continuidade a ele
    public void carregarJogo(String filename) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

            // Eh feito a leitura dos elementos presentes no arquivo e esses valores sao entao utilizados para recriar a partida
        	nomeJogador = (String) ois.readObject();
            numTentativas = ois.readInt();
            palavra = (Palavra) ois.readObject();
            numVitorias = ois.readInt();
            numDerrotas = ois.readInt();
            numAcertos = ois.readInt();
            letrasEscolhidas = (ArrayList<Character>) ois.readObject();
        }
        //Catch para quando nao eh possivel carregar o jogo
        catch (FileNotFoundException e) {
        	
            System.out.println("Nenhum jogo salvo encontrado. Iniciando um novo jogo.");
            iniciarNovoJogo(); // Iniciar uma nova partida caso o jogo nao tenha sido encontrado
            return; // Sair do metodo caso o novo jogo tenha se iniciado
            
        }
        //Catch para excecao I/O
        catch (IOException e) {
            throw e;
        }

        //Catch para o caso da classe nao ter sido encontrada
        catch (ClassNotFoundException e) {
            throw new IOException("Erro ao carregar o jogo: classe não encontrada.", e);
        }
    }

    public void mostrarDica(){
        for(int i = 0; i < palavra.getPalavra().length(); i++){
                if(!letrasEscolhidas.contains(palavra.getPalavra().charAt(i))){
                    getPalavra().getLetrasAdvinhadas().set(i, palavra.getPalavra().charAt(i));
                    numAcertos++;
                    break;
                }
            }
        }

}