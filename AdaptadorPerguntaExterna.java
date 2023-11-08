import java.util.ArrayList;
import java.util.List;

public class AdaptadorPerguntaExterna implements FontePerguntasExternas {
    private ArrayList<Pergunta> listPerguntasExternas;

    @Override
    public List<Pergunta> criaPerguntaExterna(){
        List<Pergunta> perguntasExternas = new ArrayList<>();

        perguntasExternas.add(new Pergunta("Qual a capital do Brasil?", "Geografia", new String[]{"Rio de Janeiro",
                "São Paulo", "Brasília", "Salvador"}, 2, "facil"));

        perguntasExternas.add(new Pergunta("Qual é o maior planeta do nosso sistema solar?", "Astronomia",
                new String[]{"Terra", "Júpiter", "Marte", "Vênus"}, 1, "media"));

        perguntasExternas.add(new Pergunta("Quem pintou a Mona Lisa?", "Arte",
                new String[]{"Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"}, 2, "facil"));

        perguntasExternas.add(new Pergunta("Qual é o símbolo químico para o ouro?", "Química",
                new String[]{"Au", "Ag", "Fe", "Cu"}, 0, "facil"));

        perguntasExternas.add(new Pergunta("Em que ano a Primeira Guerra Mundial começou?", "História",
                new String[]{"1914", "1916", "1918", "1920"}, 0, "media"));

        return perguntasExternas;
    }
}
