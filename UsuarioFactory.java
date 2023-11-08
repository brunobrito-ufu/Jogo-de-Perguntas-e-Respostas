public class UsuarioFactory {
    public Usuario criarUsuario(String tipo, String nome, String senha) {
        if (tipo.equalsIgnoreCase("admin")) {
            return new Admin(nome, senha);
        }
        return null; // Ou lançar uma exceção se o tipo não for reconhecido
    }
}