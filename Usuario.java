import java.io.Serializable;

public abstract class Usuario implements Serializable {       //classe abstrata usuario: não pode ser instanciada ou ter um
    private String nome;              //objeto criado a partir dessa classe, apenas pode ser herdada

    public Usuario(String nome){       //objeto da classe Usuario, passando como parametro o nome
        setNome(nome);
    }

    public String getNome() {          //metodo get do atributo nome
        return this.nome;
    }


    public void setNome(String nome) {          //metodo set do atributo nome
        this.nome = nome;
    }

    public abstract void cadastrarUser(Usuario user, Jogo jogo);  //metodo que pode ser sobre-escrito em outra classe

    public abstract boolean realizarLogin(String nome, String senha);  //metodo que pode ser sobre-escrito em outra classe
}


