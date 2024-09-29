import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Palavra implements Serializable{
    private String palavra;
    private String tema;
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
    }

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
