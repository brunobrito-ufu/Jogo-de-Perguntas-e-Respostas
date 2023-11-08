import java.io.Serializable;
import java.util.ArrayList;

public class Jogador extends Usuario implements ManipuladorUsuario, Serializable {
    private int numJogosGanhos;
    private Usuario usuario;
    private boolean jaJogou;
    public Jogador(String nome) {
        super(nome);
        this.jaJogou = false;
        this.numJogosGanhos = 0;
    }

    public void setNumJogosGanhos(int numJogosGanhos) {
        this.numJogosGanhos = numJogosGanhos;
    }

    public boolean getJaJogou() {
        return jaJogou;
    }

    public void setJaJogou(boolean valor) {
        this.jaJogou = valor;
    }

    @Override
    public String getNome() {
      return super.getNome();
    }

    @Override
    public void cadastrarUser(Usuario user, Jogo jogo) {
        ArrayList<Object> array = new ArrayList<>();
        array.add(user);
        array.add(0);
        jogo.getMapJogadoresPontos().put(user.getNome(),array);
    }

    @Override
    public boolean realizarLogin(String nome, String senha) {       //sobreposicao do metodo de login, parametros nome e senha
        return false;
    }

    public static void excluiUser(String str, Jogo jogo){           //metodo para excluir usuário, paramentros string e jogo
        jogo.getMapJogadoresPontos().remove(str);
    }

    public String exibeJogador(){           // metodo para exibir o jogador cadastrado
        String s = "Jogador ";
        s += " - "+this.getNome();
        return s;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public int getNumJogosGanhos() {
        return numJogosGanhos;
    }

}



