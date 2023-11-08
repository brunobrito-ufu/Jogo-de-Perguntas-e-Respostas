public class CalculadoraPontuacaoPadrao implements CalculadoraPontuacao {
    @Override
    public int CalculadoraPontuacao(int valorAtual, int valorPergunta, int respostaObtida, boolean perguntaComBonus,
                                 double tempo) {
        int pontuacao = valorAtual;

        // Lógica de cálculo padrão para a pontuação do jogador
        // Aqui, a pontuação é calculada com base na resposta obtida
        if (respostaObtida == valorPergunta) {
            pontuacao += 10;
        } else {
            pontuacao -= 5;
        }

        // Verifica se o jogador respondeu dentro do tempo limite e ajusta a pontuação
        if (tempo <= 10) {
            pontuacao += 15;
        }

        return pontuacao;
    }
}