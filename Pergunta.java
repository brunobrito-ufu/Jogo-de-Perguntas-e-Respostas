import java.io.Serializable;
import java.util.ArrayList;

public class Pergunta implements Serializable {
    private final int respCorreta;
    private int valorPontucao;
    private final String pergunta;
    private final String assunto;
    private final String dificuldade;
    private final ArrayList<String> alternativas = new ArrayList<>();

    public Pergunta(String pergunta, String assunto, String[] alt, int respCorreta, String dificuldade){
        this.pergunta = pergunta;
        this.assunto = assunto;
        setAlternativas(alt);
        this.respCorreta = respCorreta;
        this.dificuldade = dificuldade;
        switch (this.dificuldade) {
            case "facil" -> this.valorPontucao = 10;
            case "media" -> this.valorPontucao = 20;
            case "dificil" -> this.valorPontucao = 30;
        }
    }
    private void setAlternativas(String[] alt) {
        alternativas.add(alt[0]);
        alternativas.add(alt[1]);
        alternativas.add(alt[2]);
        alternativas.add(alt[3]);
    }

    public int getRespCorreta() {
        return respCorreta;
    }


    public String getPergunta() {
        return pergunta;
    }

    public String getAssunto() {
        return assunto;
    }

    public ArrayList getAlternativas() {
        return alternativas;
    }

    public int getValorPontucao() {
        return valorPontucao;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public String mostraPergunta(){
        String s = "Pergunta ";//+ this.idPergunta;
        s += " ("+this.assunto+"): "+this.pergunta+" ("+this.dificuldade+" - "+this.valorPontucao+" pontos)\n"+"1 - "+this.alternativas.get(0)+
                "\n2 - "+this.alternativas.get(1)+"\n3 - "+this.alternativas.get(2)+"\n4 - "+this.alternativas.get(3);
        return s;
    }
}