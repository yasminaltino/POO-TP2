import java.util.ArrayList;

public class Jogo {
    private int numTentativas;
    private Palavra palavra;
    private Forca forca;
    private int numVitorias;
    private int numDerrotas;
    private int numAcertos;
    private ArrayList<Character> letrasEscolhidas;

    public Jogo() {

        forca = new Forca();
        letrasEscolhidas = new ArrayList<>();
    }

    public Jogo(int numTentativas, Palavra palavra, Forca forca, int numVitorias, int numDerrotas, int numAcertos, ArrayList<Character> letrasEscolhidas) {
        this.numTentativas = numTentativas;
        this.palavra = palavra;
        this.forca = forca;
        this.numVitorias = numVitorias;
        this.numDerrotas = numDerrotas;
        this.numAcertos = numAcertos;
        this.letrasEscolhidas = letrasEscolhidas;
    }



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

    public Forca getForca() {
        return forca;
    }

    public void setForca(Forca forca) {
        this.forca = forca;
    }

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

    public ArrayList<Character> getLetrasEscolhidas() {
        return letrasEscolhidas;
    }

    public void setLetrasEscolhidas(ArrayList<Character> letrasEscolhidas) {
        this.letrasEscolhidas = letrasEscolhidas;
    }

    public void iniciarNovoJogo(){
        palavra = new Palavra("MELANCIA", "Frutas");
        setNumTentativas(palavra.getPalavra().length() * 2 - 5);
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



}





