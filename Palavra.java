//Importacao de diferentes classes e pacotes que serao utilizados no codigo
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Palavra implements Serializable{
    private String palavra;
    private String tema;
    private int nEspacos;
    private ArrayList<Character> letrasAdvinhadas;

//Construtores da classe Palavra
    public Palavra() {
        letrasAdvinhadas = new ArrayList<>();
    }

    public Palavra(String palavra, String tema) {
        this.palavra = palavra;
        this.tema = tema;
        atualizaLabelLetrasAdvinhadas();
    }

//Metodo responsavel por atualizar a seção que demonstra as letras inseridas pelo jogador
    public void atualizaLabelLetrasAdvinhadas(){
    	
     // Inicializa letrasAdvinhadas como uma grande sequência de '_', que marcam letras que ainda não foram descobertas
        this.letrasAdvinhadas = new ArrayList<>(Collections.nCopies(palavra.length(), '_'));
     // Como espaços e hífens não são opções selecionavéis na interface (não são letras), mas ainda assim são caracteres válidos, revela-os
        for(int i = 0; i < palavra.length(); i++)
            if(palavra.charAt(i) == ' ') {
                letrasAdvinhadas.set(i, ' ');
                nEspacos++;
            }
            else if (palavra.charAt(i) == '-') {
            	letrasAdvinhadas.set(i, '-');
                nEspacos++;
            }
    }
    
    // Sobreescreve o método toString() da classe Object para praticidade de imprimir as letras advinhadas
    @Override
    public String toString() {
    	
    	// Instancia objeto StringBuilder para auxiliar na construção da String de retorno do método
        StringBuilder formatted = new StringBuilder();
         
        for (int i = 0; i < letrasAdvinhadas.size(); i++) {
        	
        	// Insere o caractere de indice i
            formatted.append(String.format("%c", letrasAdvinhadas.get(i)));
            
            // Insere " " em seguida do caractere, exceto do último, para separá-los devidamente
            if (i < letrasAdvinhadas.size() - 1)
                formatted.append(" ");
        }
        return formatted.toString();
    }
    
    public int getNEspacos() {
    	return nEspacos;
    }
    
    /*
    public void setNEspacos(int n) {
    	this.nEspacos = n >= 0 ? n : 0;
    }
    */

    //Setters e Getters para os atributos privados
    public String getPalavra() {
        return palavra;
    }
    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
    public String getTema() {
        return tema;
    }
    public void setTema(String tema) {
        this.tema = tema;
    }
    public ArrayList<Character> getLetrasAdvinhadas() {
        return letrasAdvinhadas;
    }
    public void setLetrasAdvinhadas(ArrayList<Character> letrasAdvinhadas) {
        this.letrasAdvinhadas = letrasAdvinhadas;
    }
}
