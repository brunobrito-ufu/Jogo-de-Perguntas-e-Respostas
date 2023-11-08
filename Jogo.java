import java.io.Serializable;
import java.util.*;

public class Jogo implements Serializable {
    private ArrayList<Pergunta> listaPerguntas;   // arraylist de Perguntas
    private Map<String, ArrayList<Object>> mapJogadoresPontos;
    private int qtdRodadas;     //atributo
    private Usuario admin;      //atributo
    private int numMaxJogadores = 20;
    private ArrayList<Pergunta> perguntasSelecionadas;

    public Jogo(){                                          // construtor da classe Jogo
        listaPerguntas = new ArrayList<>();                 // criação de um arraylist
        mapJogadoresPontos = new HashMap<>();               // criação de um hashmap
        this.numMaxJogadores = 10;                          // inicia o jogo com no maximo 10 jogadores (admin pode alterar)
        this.qtdRodadas = 10;
    }

    public void setNumMaxJogadores(int numMaxJogadores) {
        this.numMaxJogadores = numMaxJogadores;
    }

    public int getNumMaxJogadores() {
        return numMaxJogadores;
    }

    public Usuario getAdmin() {                             // metodo get do atributo usuario
        return admin;
    }

    public void setAdmin(Usuario admin) {                   // metodo get do atributo usuario
        this.admin = admin;
    }

    public void setPontuacao(String nome, int pontuacao) {
        ArrayList<Object> listaValores = new ArrayList<>(getMapJogadoresPontos().values().iterator().next());

        listaValores.set(1, pontuacao); // alterando a segunda posicao(1) do arraylist

        getMapJogadoresPontos().replace(nome, listaValores);    //substituindo a pontuacao atual(0) pela nova adiquirida
    }

    public int getQtdRodadas() {                //metedo get de qtd de rodadas
        return qtdRodadas;
    }

    public void setQtdRodadas(int qtdRodadas) {                 //metedo set de qtd de rodadas
        this.qtdRodadas = qtdRodadas;
    }

    public Map<String, ArrayList<Object>> getMapJogadoresPontos() {
        return mapJogadoresPontos;
    }

    public ArrayList<Pergunta> getListaPerguntas() {
        return listaPerguntas;
    }

    public void addPergunta(Pergunta p) {               //metodo para adicionar perguntas
        this.listaPerguntas.add(p);
    }

    public void addPerguntaExt(List<Pergunta> p) {
        for (int i = 0; i < p.size() ; i++)
            this.listaPerguntas.add(p.get(i));
    }

    public void removePergunta(int p) {                     //metodo para remover perguntas
        this.listaPerguntas.remove(p - 1);
    }

    public void editaPergunta(int indexDaLista, Pergunta pergunta) {                //metodo para editar perguntas, parâmetros lista e pergunta
        this.listaPerguntas.set(indexDaLista - 1, pergunta);
    }

    public boolean isJogadorCadastrado(String user){                //metodo para verificar se o jogador está cadastrado
        if(mapJogadoresPontos.containsKey(user))
            return true;
        else
            return false;
    }

    public String mostraJogadores(){                                //metodo para mostrar jogadores cadastrados
        return (getMapJogadoresPontos().keySet()).toString();       //retornando o hashmap com cada jogador cadastrado
    }
    public String toString(){
        return mapJogadoresPontos.values().toString();
    }

    public String mostraPergunta(int i){     //metodo para mostrar perguntas cadastradas, com seu assunto, alternativas, pontuação, etc
        String s = "";
        s += "Pergunta " + (i+1);
        s += " (" + getListaPerguntas().get(i).getAssunto() + "): " + getListaPerguntas().get(i).getPergunta() + " (" + getListaPerguntas().get(i).getDificuldade() + " - " + getListaPerguntas().get(i).getValorPontucao() + " pontos)\n" + "1 - " + getListaPerguntas().get(i).getAlternativas().get(0) +
                "\n2 - " + getListaPerguntas().get(i).getAlternativas().get(1) + "\n3 - " + getListaPerguntas().get(i).getAlternativas().get(2) + "\n4 - " + getListaPerguntas().get(i).getAlternativas().get(3) + "\n";
        return s;
    }
    public String mostraPerguntas(){
        String s = "";
        for (int i = 0; i < listaPerguntas.size(); i++) {
            s += "Pergunta " + (i+1);
            s += " (" + getListaPerguntas().get(i).getAssunto() + "): " + getListaPerguntas().get(i).getPergunta() + " (" + getListaPerguntas().get(i).getDificuldade() + " - " + getListaPerguntas().get(i).getValorPontucao() + " pontos)\n" + "1 - " + getListaPerguntas().get(i).getAlternativas().get(0) +
                    "\n2 - " + getListaPerguntas().get(i).getAlternativas().get(1) + "\n3 - " + getListaPerguntas().get(i).getAlternativas().get(2) + "\n4 - " + getListaPerguntas().get(i).getAlternativas().get(3) + "\n";
        }
        return s;
    }

    public boolean getJogadorJajogou(String user){      //metodo get para conferir se um jogador já jogou ou não
       return ((Jogador) this.getMapJogadoresPontos().get(user).get(0)).getJaJogou();
    }

    public void setJogadorJajogou(String user, boolean val){         ////metodo set para alterar o atributo se um jogador já jogou ou não
        ((Jogador) this.getMapJogadoresPontos().get(user).get(0)).setJaJogou(val);
    }

    public void setMapJogadoresPontos(Map<String, ArrayList<Object>> mapJogadoresPontos) {
        this.mapJogadoresPontos = mapJogadoresPontos;
    }

    public void setListaPerguntas(ArrayList<Pergunta> listaPerguntas) {
        this.listaPerguntas = listaPerguntas;
    }

    public void selecionaPerguntas(ArrayList<Pergunta> lista, int qtdRodadas) {
        Random rand = new Random();
        ArrayList<Pergunta> lista2 = lista;

        if(qtdRodadas > lista2.size()){
            return;
        }

        ArrayList<Pergunta> novaLista = new ArrayList<Pergunta>();
        for (int i = 0; i < qtdRodadas; i++) {

            int randomIndex = rand.nextInt(lista2.size());

            novaLista.add((Pergunta) lista2.get(randomIndex));

            lista2.remove(randomIndex);
        }
        this.perguntasSelecionadas = novaLista;
    }

    public ArrayList<Pergunta> getPerguntasSelecionadas() {
        return perguntasSelecionadas;
    }
}
