public class AdminDecorator extends Admin {
    private Admin admin;

    public AdminDecorator(Admin admin) {
        super(admin.getNome(), admin.getSenha());
        this.admin = admin;
    }
    public static void excluiUser(String str, Jogo jogo){           //metodo para excluir usuário, paramentros string e jogo
        jogo.getMapJogadoresPontos().remove(str);
    }
}