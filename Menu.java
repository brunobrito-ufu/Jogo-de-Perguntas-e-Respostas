import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Menu {
    private Jogo jogo = new Jogo();
    private Pergunta pergunta;
    private Admin admin;
    private Jogador jogador;
    private Arquivos arquivo = Arquivos.getInstancia(); // Singleton
    private Scanner sc = new Scanner(System.in);
    private CalculadoraPontuacao calculadoraPontuacao; // Strategy
    private UsuarioFactory usuarioFactory = new UsuarioFactory(); // Factory
    private AdaptadorPerguntaExterna adaptador = new AdaptadorPerguntaExterna(); // Adaptor

    public Usuario criarUsuario(String nome, int tipoUsuario) {
        if (tipoUsuario == 1) {
            Jogador jogador = new Jogador(nome);
            jogador.cadastrarUser(jogador, jogo);
            return jogador;
        } else if (tipoUsuario == 2) {
            System.out.print("Defina uma senha para seu usuário: ");
            String senha = sc.next();
            Usuario novoUsuario = usuarioFactory.criarUsuario("admin", nome, senha);
            novoUsuario.cadastrarUser(novoUsuario, jogo); // Certifica-se de que o Admin seja registrado no jogo
            return novoUsuario;
        } else {
            return null; // Retornar null para tratamento de erro ou outro tratamento apropriado
        }
    }
    public void cadastraUsuario() {
        if (jogo == null) {
            System.out.println("O jogo não foi inicializado. Por favor, inicialize o jogo primeiro.");
            return;
        }
        if(jogo.getMapJogadoresPontos().size() == jogo.getNumMaxJogadores()){
            System.out.println("O jogo já atingiu o seu máximo de jogadores cadastrados.");
            return;
        }

        String senha;
        System.out.println("(Digite 0 para cancelar a ação)");
        System.out.print("Digite o nome do seu usuario: ");
        String nome = sc.next().toLowerCase();

        if (nome.equals("0"))
            return;

        while (jogo.getMapJogadoresPontos().containsKey(nome)) {
            System.out.println("Usuário já existente! Digite um nome diferente: ");
            nome = sc.next().toLowerCase();
        }

        int resp;
        do {
            try {
                System.out.print("Esse usuario é um jogador(1) ou um admin(2)? ");
                resp = sc.nextInt();
                if (resp != 1 && resp != 2) {
                    System.out.print("Escolha apenas 1 ou 2: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Insira apenas números inteiros."); // EXCEÇÃO OK
                sc.nextLine(); // Limpa o buffer do Scanner
                resp = 0; // Define um valor inválido para repetir o loop
            }
        } while (resp != 1 && resp != 2);

        Usuario novoUsuario = criarUsuario(nome, resp);

        if (novoUsuario != null) {
            System.out.println("Usuário cadastrado. Aperte qualquer tecla para continuar...");
            sc.nextLine();
        } else {
            System.out.println("Tipo de usuário inválido. Operação cancelada.");
        }
    }

    public boolean verificaAdmin() {
        System.out.print("Digite o Usuario: ");
        String user = sc.next();
        System.out.print("Senha: ");
        String senha = sc.next();

        return jogo.getAdmin().realizarLogin(user, senha);
    }

    public void areaAdmin() {
        int op1 = 0;
        while (op1 != 12) {

            System.out.println("=== AREA DO ADMINISTRADOR ===");
            System.out.println("1. Excluir jogador");
            System.out.println("2. Mostrar jogadores");
            System.out.println("3. Definir quantidade de rodadas");
            System.out.println("4. Adicionar pergunta");
            System.out.println("5. Excluir pergunta");
            System.out.println("6. Editar pergunta");
            System.out.println("7. Mostrar perguntas");
            System.out.println("8. Ver jogadores que não jogaram ainda");
            System.out.println("9. Encerrar jogo");
            System.out.println("10. Definir limite de jogadores");
            System.out.println("11. Adicionar Perguntas externas");
            System.out.println("12. Voltar para o menu");
            System.out.print("Digite a opcao desejada: ");

            op1 = leInteiroEntre(1, 12);


            switch (op1) {
                case 1 -> {
                    this.excluiUsuario();
                    sc.nextLine();
                }
                case 2 -> this.mostraJogadores();

                case 3 -> {
                    this.definirQtdRodadas();
                    sc.nextLine();
                }
                case 4 -> this.adicionarPergunta();

                case 5 -> {
                    while (true) {
                        try {
                            this.excluiPergunta();
                            sc.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada invalida. Aperte qualquer tecla para continuar...");
                            sc.nextLine();
                            sc.nextLine();
                            break;
                        }
                    }
                }
                case 6 -> {
                    while (true) {
                        try {
                            this.editaPergunta();
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada invalida. Aperte qualquer tecla para continuar...");
                        }
                        sc.nextLine();
                        break;
                    }
                }
                case 7 -> this.imprimeListaPerguntas();
                case 8 -> {
                    this.jogadoresNaoJogaram();
                    sc.nextLine();
                }
                case 9 -> {
                    this.encerrarJogo();
                    sc.nextLine();
                }
                case 10 -> {
                    System.out.println("Qual o numero maximo de jogadores? (Digite 0 se quiser cancelar a ação)\n");
                    int x = leInteiroEntre(0,99);
                    if (x == 0)
                        break;
                    jogo.setNumMaxJogadores(x);
                }
                case 11 -> this.adicionarPerguntaExterna();
            }
        }
    }

    public void excluiUsuario() {
        System.out.println("\nJogadores = " + jogo.mostraJogadores());
        System.out.println("Qual desses jogadores voce deseja excluir? (Digite 0 se quiser cancelar a ação)");
        String jogExcl = sc.next();
        int resp = 0;

        if (jogExcl.equals("0"))
            return;

        else if (jogo.getMapJogadoresPontos().containsKey(jogExcl)) {   // se o nome digitado for igual a algum nome
            AdminDecorator.excluiUser(jogExcl, jogo);
            System.out.println("Jogador excluído. Aperte qualquer tecla para continuar...");
            sc.nextLine();
        } else {
            System.out.println("Jogador nao encontrado. Digite (1) para tentar novamente ou (2) para voltar para a area do admin: ");
            while (resp != 1 && resp != 2) {
                try {
                    resp = sc.nextInt();
                    sc.nextLine();
                    if (resp == 1)
                        excluiUsuario();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite (1) para tentar novamente ou (2) para voltar para a area do admin: ");
                    sc.nextLine();
//                break;
                }
            }
        }
    }


    public void mostraJogadores() {
        System.out.println("\nJogadores = " + jogo.mostraJogadores());
        System.out.print("Lista impressa. Aperte qualquer tecla para continuar...");
        sc.nextLine();
    }

    public void definirQtdRodadas() {
        int rod, resp;
        if(jogo.getQtdRodadas() != 0) {
            System.out.print("Foi definido que o jogo terá " + jogo.getQtdRodadas() + ". Deseja redefinir? SIM(1) NAO(2) CANCELAR(3)");
            resp = leInteiroEntre(1,3);
            if (resp == 3)
                return;
            else if (resp == 2) {
                System.out.print("OK!");
                return;
            }
        }
        while (true) {
            try {
                System.out.print("Defina a quantidade de rodadas que tera no jogo: ");
                rod = sc.nextInt();
                sc.nextLine();
                if (rod > jogo.getListaPerguntas().size())
                    System.out.print("O jogo não pode ter mais rodadas do que perguntas cadastradas. Aperte qualquer tecla para continuar...");
                else if (rod < 1)
                    System.out.print("O jogo deve ter ao menos uma rodada. Aperte qualquer tecla para continuar...");
                else {
                    jogo.setQtdRodadas(rod);
                    System.out.printf("O jogo terá %d rodadas. Aperte qualquer tecla para continuar...", rod);
                    jogo.selecionaPerguntas(jogo.getListaPerguntas(), jogo.getQtdRodadas());
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Tente novamente com um valor numérico válido.");
                sc.nextLine();
            }
        }
    }

    public void adicionarPerguntaExterna() {
        try {
            List<Pergunta> perguntasAdaptadas = adaptador.criaPerguntaExterna();
            jogo.addPerguntaExt(perguntasAdaptadas);
            System.out.print("Pergunta adicionada. Aperte qualquer tecla para continuar...");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao adicionar pergunta. Verifique os dados informados.");
        }
    }
    public void adicionarPergunta() {
        try {
            System.out.print("(Digite 0 se quiser cancelar a ação)\nDigite sua pergunta: ");
            String perg = sc.nextLine();
            if (perg.equals("0"))
                return;

            System.out.print("Digite o assunto da pergunta: ");
            String assunto = sc.nextLine();

            String[] alternativas = new String[4];
            for (int i = 0; i < 4; i++) {
                System.out.print((i + 1) + ": ");
                alternativas[i] = sc.nextLine();
            }

            int resposta;
            do {
                System.out.print("Qual dessas alternativas é a correta? ");
                try {
                    resposta = sc.nextInt();
                    if (resposta < 1 || resposta > 4) {
                        System.out.println("Opção inválida. Digite um número entre 1 e 4.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida. Digite um número entre 1 e 4.");
                    resposta = -1; // Define uma resposta inválida para continuar o loop
                }
                sc.nextLine(); // Limpa o buffer do Scanner
            } while (resposta < 1 || resposta > 4);

            int tentativas = 0;
            System.out.print("Qual é a dificuldade dessa pergunta (facil/media/dificil)? ");
            String dificuldade = sc.nextLine().toLowerCase();
            while (!dificuldade.equals("facil") && !dificuldade.equals("media") && !dificuldade.equals("dificil") && tentativas < 3) {
                System.out.print("Entrada inválida. Escolha apenas uma das tres opções: ");
                dificuldade = sc.nextLine().toLowerCase();
                tentativas++;
                if (tentativas == 3) {
                    System.out.print("Tentativas esgotadas.");
                    return;
                }
            }

            Pergunta pergunta = new Pergunta(perg, assunto, alternativas, resposta, dificuldade);
            jogo.addPergunta(pergunta);

            System.out.print("Pergunta adicionada. Aperte qualquer tecla para continuar...");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao adicionar pergunta. Verifique os dados informados.");
        }
    }


    public void excluiPergunta() {
        System.out.print(jogo.mostraPerguntas());
        System.out.print("Qual dessas perguntas voce deseja excluir? (Digite 0 se quiser cancelar a ação)");
        int p = leInteiroEntre(0, jogo.getListaPerguntas().size());
        try {
            if (p == 0)
                return;
            jogo.removePergunta(p);
            System.out.print("Pergunta excluida. Aperte qualquer tecla para continuar...");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao excluir pergunta: " + e.getMessage());
            sc.nextLine();
        }
    }
    public void editaPergunta() {
        System.out.print(jogo.mostraPerguntas());
        int pIndex;
        while (true) {
            try {
                System.out.print("Qual dessas perguntas voce deseja editar? (Digite 0 se quiser cancelar a ação)");
                pIndex = leInteiroEntre(0, jogo.getListaPerguntas().size());
                if (pIndex == 0)
                    return;
                else if (jogo.getListaPerguntas().size() == 0){
                    System.out.println("O jogo nao possui perguntas para editar.");
                    break;
                }

                else if (pIndex < 1 || pIndex > jogo.getListaPerguntas().size()) {
                    System.out.println("Opção inválida. Digite um número entre 1 e " + jogo.getListaPerguntas().size());
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um numero.");
                sc.nextLine();
            }
        }
        sc.nextLine();

        System.out.print("Digite sua nova pergunta: ");
        String perg = sc.nextLine();

        System.out.print("Digite o assunto da pergunta: ");
        String assunto = sc.nextLine();

        String[] alternativas = new String[4];
        for (int i = 0; i < 4; i++) {
            System.out.print((i + 1) + ": ");
            alternativas[i] = sc.nextLine();
        }
        int resposta;
        while (true) {
            try {
                System.out.print("Qual dessas alternativas é a correta? ");
                resposta = sc.nextInt();
                if (resposta < 1 || resposta > 4) {
                    System.out.println("Opção inválida. Digite um número entre 1 e 4.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
                sc.nextLine();
            }
        }
        sc.nextLine();

        System.out.print("Qual eh a dificuldade dessa pergunta (facil/media/dificil)? ");
        String dificuldade = sc.nextLine().toLowerCase();
        while (!dificuldade.equals("facil") && !dificuldade.equals("media") && !dificuldade.equals("dificil")) {
            System.out.print("facil, media ou dificil? ");
            dificuldade = sc.nextLine().toLowerCase();
        }
        pergunta = new Pergunta(perg, assunto, alternativas, resposta, dificuldade);
        jogo.editaPergunta(pIndex, pergunta);

        System.out.print("Pergunta editada. Aperte qualquer tecla para continuar...");
    }

    public void imprimeListaPerguntas() {
        if (jogo.getListaPerguntas().isEmpty())
            System.out.println("\nNao ha perguntas cadastradas. Aperte qualquer tecla para continuar...");
        else {
            System.out.println(jogo.mostraPerguntas());
            System.out.print("Lista impressa. Aperte qualquer tecla para continuar...");
        }
        sc.nextLine();
    }

    private int leInteiroEntre(int min, int max) {
        int valor;
        while (true) {
            try {
                valor = sc.nextInt();
                sc.nextLine(); // Limpa o buffer de entrada

                if (valor >= min && valor <= max) {
                    break;
                } else {
                    System.out.print("Valor inválido. Digite um número entre " + min + " e " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Digite um número válido: ");
                sc.nextLine(); // Limpa o buffer de entrada
            }
        }
        return valor;
    }
    int pulos = 3;
    public int respPerg(int idx, AtomicLong tempoGasto) {
        System.out.print("\n"+jogo.mostraPergunta(idx));

        final int[] resp = new int[1];
        resp[0] = -1;

        if (pulos > 0) {
            System.out.print("\n5- PULAR");
            System.out.println("\n6- DESISTIR");
        }
        else
            System.out.println("\n5- DESISTIR");


        Thread responseThread = new Thread(() -> {
            try {
                System.out.print("Resposta: ");
                synchronized (sc) {
                    if (pulos > 0)
                        resp[0] = leInteiroEntre(1,6);
                    else
                        resp[0] = leInteiroEntre(1,5);
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro ao ler a resposta. Certifique-se de inserir um número válido.");
            }
        });

        responseThread.start();

        long startTime = System.currentTimeMillis(); // Contagem de início

        try {
            responseThread.join(20000); // Aguarda a resposta por 20 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis(); // Tempo de término
        long elapsedTime = endTime - startTime; // Tempo decorrido em milissegundos
        tempoGasto.set(elapsedTime);    // atrubui ao parametro o tempo gasto na execução do processo

        if (responseThread.isAlive()) {
            // se o tempo de resposta expira, interrompe a thread e define resp[0] = -1
            responseThread.interrupt();
            resp[0] = -1;
            System.out.println("Tempo expirado! Você não respondeu dentro dos 20s.");

        }
        if (resp[0] == 5)
            pulos--;

        return resp[0];
    }

    public void regrasJogo(){
        System.out.println("-> O Administrador responsável pelo jogo definirá a quantidade de rodadas que os jogadores disputarão por jogo;");//OK
        System.out.println("-> Você possui apenas 20s para responder cada pergunta. Se ultrapassar, a pergunta sera considerada ERRADA;");//OK
        System.out.println("-> A cada 3 respostas corretas, a próxima pergunta valerá o dobro de pontos;");//OK
        System.out.println("-> Se responder as perguntas em menos de 5s, elas valerão 1/3 a mais da pontuação;");//OK
        System.out.println("-> O jogador so pode receber um bönus por vez. Ou de tempo, ou de sequëncia.;");//OK
        System.out.println("-> Se você pular, terá uma penalização de 5 pts em cada pergunta. Porém você só pode pular tres vezes;");//OK
        System.out.println("-> Se errar, perderá 10 pts, independente da dificuldade da pergunta. OBS: apesar de ser menor a penalidade, um pulo corresponde a um erro;");//OK
        System.out.println("-> Para ser justo, a cada 3 respostas incorretas, você perderá 20pt.");//OK
        sc.nextLine();
    }

    public String entradaJogo(){

        System.out.print("Digite seu usuário: ");
        String user = sc.next();
        jogo.setJogadorJajogou(user,false);

        try {
            if (!jogo.isJogadorCadastrado(user)) {
                sc.nextLine();
                System.out.println("Jogador não cadastrado. Volte ao menu e aperte (1) para se cadastrar...");
                return entradaJogo();
            }else if (jogo.getJogadorJajogou(user)) {
                sc.nextLine();
                System.out.println("Esse usuário já jogou. Escolha outro usuário para jogar.");
                return entradaJogo();
            }else {
                System.out.print("Seja muito bem-vindo(a), divirta-se " + user + "! Você já conhece as regras do jogo? (1)SIM  (2)NÃO");
                int regra = leInteiroEntre(1, 2);
                if (regra == 2)
                    regrasJogo();

                System.out.println("Ok! Aperte qualquer tecla para iniciar seu jogo...");
                sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return user;
        }

        return user;
    }
    public void iniciaJogo() {

        int rodadas = jogo.getQtdRodadas();
        if (rodadas == 0) {
            System.out.print("Defina a quantidade de rodadas que tera no jogo antes de iniciá-lo");
            return;
        }
        if (rodadas > jogo.getListaPerguntas().size()) {
            System.out.print("O jogo não pode ter mais rodadas do que perguntas cadastradas.");
            return;
        }
        
        String user;
        try {
            user = entradaJogo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        int valor = 0;
        int respCorretConsec = 0;
        int respErradConsec = 0;
        int respCorretTotal = 0;
        int respErradTotal = 0;
        int pular = 0;
        boolean PergComBonus = false;
        boolean proxPergComOnus = false;

        for (int i = 0; i < rodadas; i++) {
            int valorPerg = jogo.getPerguntasSelecionadas().get(i).getValorPontucao();
            int valorBonus = 2 * valorPerg;
            AtomicLong tempoGasto = new AtomicLong();
            int respObtida = this.respPerg(i, tempoGasto);
            int tempo = (int) tempoGasto.get()/1000;


//          DAQUI PRA BAIXO A RESPOSTA ESTÁ CORRETA
            if (respObtida == jogo.getPerguntasSelecionadas().get(i).getRespCorreta()) {
                    System.out.print("RESPOSTA CORRETA!!! ");
                    respCorretConsec++;
                    respCorretTotal++;
                    respErradConsec = 0;
                    proxPergComOnus = false;


                if (tempo < 5 && !PergComBonus) {
                    System.out.println("Você respondeu antes de 5 segundos e ganhou 1/3 da pontuação de bônus adicional!");
                    valorPerg = valorPerg + (valorPerg / 3);
                    System.out.println("+" +valorPerg+ "pts.");
                }

                if (!PergComBonus && tempo >= 5)
                    System.out.println("+" +valorPerg + "pts.");



                valor += PergComBonus ? valorBonus : valorPerg;
                //se tiver bonus, "valor" recebe valorBonus, se nao, recebe valorPerg

                if (PergComBonus) {
                    calculadoraPontuacao = new CalculadoraPontuacaoComBonus();
                    valorBonus = calculadoraPontuacao.CalculadoraPontuacao(valor, valorPerg, respObtida, PergComBonus,tempo);
                    System.out.println("+" + valorBonus + "pts.");
                    PergComBonus = false;
                }

                if (respCorretConsec == 3 && i<rodadas-1) {
                    PergComBonus = true;
                    System.out.println("BONUS CONQUISTADO! Sua próxima pergunta valerá o dobro em caso de acerto!");
                    respCorretConsec = 0;
                }


//          DAQUI PRA BAIXO A RESPOSTA ESTÁ ERRADA

            }else {
                respErradTotal++;
                valor -= 10;

                if (proxPergComOnus) {
                    System.out.println("3 RESPOSTAS ERRADAS CONSECUTIVAS! -20pt.");
                    valor -= 20;
                    respErradConsec = 0;
                    proxPergComOnus = false;

                } else if (respObtida == -1) {
                    System.out.println("TEMPO EXPIRADO! -10pt.");
                    valor -= 10;
                    respCorretConsec = 0;
                    PergComBonus = false;
                    if (respErradConsec == 1) {
                        proxPergComOnus = true;
                        System.out.println("CUIDADO! Você errou duas respostas seguidas. Se errar a próxima perderá 20pt!\n");
                    }
                    respErradConsec++;

                } else if (pular < 3 && respObtida == 5) {
                    System.out.println("PULOU! -5pt.");
                    valor -= 5;
                    respCorretConsec = 0;
                    PergComBonus = false;
                    pular++;
                    System.out.println("Pulos restantes: " + (3 - pular));
                    if (respErradConsec == 1) {
                        proxPergComOnus = true;
                        System.out.println("CUIDADO! Você errou duas respostas seguidas. Se errar a próxima perderá 20pt!\n");
                    }
                    respErradConsec++;

                } else if ((pular == 3 && respObtida == 5) || (pular < 3 && respObtida == 6)) {
                    System.out.print("Você Desistiu do jogo. Sua pontuação sera zerada.");
                    jogo.setPontuacao(user, 0);
                    return;
                } else {
                    System.out.println("RESPOSTA ERRADA! -10pts.");
                    valor -= 10;
                    respCorretConsec = 0;
                    PergComBonus = false;
                    if (respErradConsec == 1) {
                        proxPergComOnus = true;
                        System.out.println("CUIDADO! Você errou duas respostas seguidas. Se errar a próxima perderá 20pt!\n");
                    }
                    respErradConsec++;
                }
            }

            System.out.println("Tempo gasto: " + tempo + " segundos");
        }

        if (valor < 0)
            valor = 0;

        jogo.setPontuacao(user, valor);
        System.out.println("\nJogo finalizado. Você acertou "+respCorretTotal+" perguntas e errou "+respErradTotal+".\nSua pontuação total foi de " + valor + " pontos! Aperte qualquer tecla para voltar ao menu...");
        jogo.setJogadorJajogou(user, true);

    }

    public void rankingJogo() {
        ArrayList<Map.Entry<String, ArrayList<Object>>> ranking = new ArrayList<>(jogo.getMapJogadoresPontos().entrySet());

        // Ordenar a lista pelo segundo valor (pontuação) em ordem decrescente
        ranking.sort(new Comparator<Map.Entry<String, ArrayList<Object>>>() {
            public int compare(Map.Entry<String, ArrayList<Object>> entry1, Map.Entry<String, ArrayList<Object>> entry2) {
                int pontuacao1 = (int) entry1.getValue().get(1);
                int pontuacao2 = (int) entry2.getValue().get(1);
                return Integer.compare(pontuacao2, pontuacao1);
            }
        });
        System.out.println("Ranking do jogo:");
        int posicao = 1;
        for (Map.Entry<String, ArrayList<Object>> entry : ranking) {
            String nomeJogador = entry.getKey();
            int pontuacao = (int) entry.getValue().get(1);
            if (!jogo.getJogadorJajogou(nomeJogador))
                System.out.println("Posição " + posicao + ": " + nomeJogador + " - Pontuação: 0 (não jogou)");
            else
                System.out.println("Posição " + posicao + ": " + nomeJogador + " - Pontuação: " + pontuacao);
            posicao++;
        }
        System.out.println("Aperte qualquer botao para continuar...");
    }


    public void vitoria(){
        int maiorPonto = 0;
        String nomeJogador = null;

        for (Map.Entry<String, ArrayList<Object>> entry : jogo.getMapJogadoresPontos().entrySet()) {
            nomeJogador = entry.getKey();
            int pontos = (int) jogo.getMapJogadoresPontos().get(nomeJogador).get(1);

            if(pontos > maiorPonto)
                maiorPonto = pontos;

        }
        Jogador j = (Jogador) jogo.getMapJogadoresPontos().get(nomeJogador).get(0);
        ((Jogador) jogo.getMapJogadoresPontos().get(nomeJogador).get(0)).setNumJogosGanhos(j.getNumJogosGanhos() + 1);
        System.out.println("nome: "+j.getNome());
        System.out.println("num vitorias depois: "+j.getNumJogosGanhos());
    }

    public void jogadoresNaoJogaram(){
        System.out.println("Jogadores que ainda nao jogaram: ");
        for (Map.Entry<String, ArrayList<Object>> entry : jogo.getMapJogadoresPontos().entrySet()) {
            String nomeJogador = entry.getKey();
            if (!jogo.getJogadorJajogou(nomeJogador))
                System.out.println(nomeJogador);
        }
        System.out.print("Aperte qualquer tecla para retornar.");
}

    public void encerrarJogo(){
        int resp;

        this.salvarFechar();
        jogo.selecionaPerguntas(jogo.getListaPerguntas(), jogo.getQtdRodadas());
        System.out.print("Ok! ");
        this.rankingJogo();

        System.out.print("\nDeseja começar um novo jogo(1) Fechar o programa(2) Voltar ao menu(3)");
        resp = leInteiroEntre(1,3);
        if (resp == 3)
            return;

        else if (resp == 1) {
            for (Map.Entry<String, ArrayList<Object>> entry : jogo.getMapJogadoresPontos().entrySet()) {
                String nomeJogador = entry.getKey();
                if (jogo.getJogadorJajogou(nomeJogador)) {
                    jogo.setPontuacao(nomeJogador, 0);
                    jogo.setJogadorJajogou(nomeJogador, false);  //aqui tem que ser false
                }
            }

            System.out.println("Aperte qualquer tecla para continuar. ");
        }
        else {
            System.out.println("Jogo encerrado.");
            System.exit(0);
        }

    }

    public void rankingGeral() {

        arquivo.EscritaJogadores(jogo);

        ArrayList<Map.Entry<String, ArrayList<Object>>> ranking = new ArrayList<>(arquivo.LeituraJogador().entrySet());

        // Ordenar a lista pelo segundo valor (numJogosGanhos) em ordem decrescente
        ranking.sort(new Comparator<Map.Entry<String, ArrayList<Object>>>() {
            public int compare(Map.Entry<String, ArrayList<Object>> entry1, Map.Entry<String, ArrayList<Object>> entry2) {
                Jogador j1 = (Jogador) entry1.getValue().get(0);
                Jogador j2 = (Jogador) entry2.getValue().get(0);
                int pontuacao1 = j1.getNumJogosGanhos();
                int pontuacao2 = j2.getNumJogosGanhos();
                return Integer.compare(pontuacao2, pontuacao1);
            }
        });
        System.out.println("Ranking do jogo:");
        int posicao = 1;
        for (Map.Entry<String, ArrayList<Object>> entry : ranking) {
            String nomeJogador = entry.getKey();
            int pontuacao = (int) entry.getValue().get(1);
            Jogador j1 = (Jogador) entry.getValue().get(0);
            int vitorias = j1.getNumJogosGanhos();
            if (!jogo.getJogadorJajogou(nomeJogador))
                System.out.println("Posicao " + posicao + ": " + nomeJogador + "->  Vitorias: " + vitorias + " - Pontuacao Total: 0");
            else
                System.out.println("Posicao " + posicao + ": " + nomeJogador + "->  Vitorias: " + vitorias + " - Pontuacao Total: " + pontuacao);
            posicao++;
        }
        System.out.println("Aperte qualquer botao para continuar...");
    }

    public void salvarFechar(){
        arquivo.EscritaJogadores(jogo);
        arquivo.EscritaPerguntas(jogo);
        arquivo.EscritaAdmin(jogo);
    }

    public void preencherCampos(){
        arquivo.preencheJogadores(jogo);
        arquivo.LeituraPergunta(jogo);
        arquivo.LeituraAdmin(jogo);
        jogo.selecionaPerguntas(jogo.getListaPerguntas(), jogo.getQtdRodadas());
    }


}


