import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        // MENU INICIAL:
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        int op = 0;

        menu.preencherCampos();

        while (op != 7) {            System.out.println("=== MENU INICIAL ===");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Área do Admin");
            System.out.println("3. Regras do Jogo");
            System.out.println("4. Iniciar Jogo");
            System.out.println("5. Mostrar ranking de jogos");
            System.out.println("6. Mostrar ranking da partida");
            System.out.println("7. Fechar");
            System.out.print("Digite a opcao desejada: ");
            try {
                op = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um valor numérico válido.");
                sc.nextLine();
            }

            switch (op) {
                case 1:
                    menu.cadastraUsuario();
                    sc.nextLine();
                    break;

                case 2:
                    int tentativas = 0;
                    while (true) {
                        try {
                            if(tentativas >= 3)
                                break;
                            else if (menu.verificaAdmin()) {
                                System.out.println("Sucesso!!");
                                menu.areaAdmin();
                                break;
                            } else {
                                tentativas++;
                                System.out.println("Usuario ou Senha incorreta! Voce tem mais "+(3-tentativas)+" tentativas.");
                            }
                        } catch (NullPointerException e) {
                            System.out.println("Nao ha administradores no sistema!! Volte no menu e aperte (1) para cadastrar.");
                            break;
                        }
                    }
                    break;

                case 3:
                    menu.regrasJogo();
                    break;
                case 4:
                    menu.iniciaJogo();
                    sc.nextLine();
                    break;
                case 5:
                    menu.rankingGeral();
                    menu.vitoria();
                    sc.nextLine();
                    break;
                case 6:
                    menu.rankingJogo();
                    sc.nextLine();
                    break;
                case 7:
                    menu.salvarFechar();
            }
        }

    }
}
















