import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Palavra implements Serializable{
    private String palavra;
    private String tema;
    private int nEspacos;
    private ArrayList<Character> letrasAdvinhadas;

    public Palavra() {
        letrasAdvinhadas = new ArrayList<>();
    }

    public Palavra(String palavra, String tema) {
        this.palavra = palavra;
        this.tema = tema;
        atualizaLabelLetrasAdvinhadas();

    }

    public void atualizaLabelLetrasAdvinhadas(){
        this.letrasAdvinhadas = new ArrayList<>(Collections.nCopies(palavra.length(), '_'));
        
        for(int i = 0; i < palavra.length(); i++)
            if(palavra.charAt(i) == ' ') {
                letrasAdvinhadas.set(i, ' ');
                nEspacos++;
            }
    }
    
    @Override
    public String toString() {
    	String output = "";
    	
    	for (Character c : letrasAdvinhadas) {
    		output += c;
    		output += ' ';
    	}
    	
    	return output;
    }
    
    public int getNEspacos() {
    	return nEspacos;
    }
    
    /*
    public void setNEspacos(int n) {
    	this.nEspacos = n >= 0 ? n : 0;
    }
    */

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
//d