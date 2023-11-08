public class CalculadoraPontuacaoComBonus implements CalculadoraPontuacao {
//    public CalculadoraPontuacaoComBonus(int valor, int valorPerg, int respObtida, boolean pergComBonus, double tempo) {
//    }

    @Override
    public int CalculadoraPontuacao(int valorAtual, int valorPergunta, int respostaObtida, boolean perguntaComBonus,
                                 double tempo) {
        int pontuacao = valorAtual;

        // Lógica de cálculo para a pontuação do jogador com bônus
        // Aqui, a pontuação é calculada com base na resposta obtida e sempre inclui o valor da pergunta
        pontuacao += valorPergunta;

        // Verifica se a pergunta possui bônus e ajusta a pontuação
        if (perguntaComBonus) {
            pontuacao += 30;
        }

        // Verifica se o jogador respondeu dentro do tempo limite e ajusta a pontuação
        if (tempo <= 10) {
            pontuacao += 20;
        }

        return pontuacao;
    }
}
