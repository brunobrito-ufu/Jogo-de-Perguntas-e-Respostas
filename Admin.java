import java.io.Serializable;

public class Admin extends Usuario implements ManipuladorUsuario, Serializable {
    private String senha;                                  // Atributo senha

    public Admin(String nome, String senha) {              // Construtor da classe Adm, utilizando o atributo nome da classe Usuário
        super(nome);
        setSenha(senha);
    }

    public String getSenha() {                      // Método get do atributo senha
        return senha;
    }

    public void setSenha(String senha) {                // Método set do atributo senha
        this.senha = senha;
    }

    @Override
    public void cadastrarUser(Usuario user, Jogo jogo) {        // Sobreposição do método cadastraUser
        if (user instanceof Admin) { // armazenamento do Admin, Admin atribuído corretamente à instância do jogo.
            jogo.setAdmin(user);
        }
    }

    public boolean realizarLogin(String nome, String senha) {           // Método para login, passando como parâmetro o nome e a senha do admin.
        return this.getNome().equals(nome) && this.senha.equals(senha);
    }
}

