import model.Cliente;
import model.Conta;
import service.BancoServico;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static BancoServico banco = new BancoServico();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA BANCÁRIO SIMPLES ===");

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarConta();
                    break;
                case 3:
                    realizarDeposito();
                    break;
                case 4:
                    realizarSaque();
                    break;
                case 5:
                    realizarTransferencia();
                    break;
                case 6:
                    consultarSaldo();
                    break;
                case 7:
                    aplicarRendimento();
                    break;
                case 8:
                    listarContas();
                    break;
                case 9:
                    exibirRelatorio();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Conta");
        System.out.println("3. Realizar Depósito");
        System.out.println("4. Realizar Saque");
        System.out.println("5. Realizar Transferência");
        System.out.println("6. Consultar Saldo");
        System.out.println("7. Aplicar Rendimento na Poupança");
        System.out.println("8. Listar Contas");
        System.out.println("9. Relatório de Consolidação");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarCliente() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();
        banco.cadastrarCliente(nome, cpf);
    }

    private static void cadastrarConta() {
        List<Cliente> clientes = banco.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }

        System.out.println("\nClientes cadastrados:");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            System.out.println((i + 1) + ". " + cliente.getNome() + " - CPF: " + cliente.getCpf());
        }

        System.out.print("\nDigite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = banco.buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado. Verifique o CPF.");
            return;
        }

        System.out.print("Digite o tipo de conta (corrente/poupanca): ");
        String tipo = scanner.nextLine();

        if (!tipo.equalsIgnoreCase("corrente") && !tipo.equalsIgnoreCase("poupanca")) {
            System.out.println("Tipo de conta inválido! Use 'corrente' ou 'poupanca'.");
            return;
        }

        System.out.print("Digite o saldo inicial: ");
        double saldo = scanner.nextDouble();
        scanner.nextLine();

        if (saldo < 0) {
            System.out.println("Saldo inicial não pode ser negativo.");
            return;
        }

        banco.cadastrarConta(cliente, tipo, saldo);
    }

    private static void realizarDeposito() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor <= 0) {
            System.out.println("Valor de depósito deve ser positivo.");
            return;
        }

        banco.depositar(numero, valor);
    }

    private static void realizarSaque() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Digite o valor do saque: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor <= 0) {
            System.out.println("Valor de saque deve ser positivo.");
            return;
        }

        banco.sacar(numero, valor);
    }

    private static void realizarTransferencia() {
        System.out.print("Digite o número da conta origem: ");
        int origem = scanner.nextInt();
        System.out.print("Digite o número da conta destino: ");
        int destino = scanner.nextInt();
        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor <= 0) {
            System.out.println("Valor de transferência deve ser positivo.");
            return;
        }

        if (origem == destino) {
            System.out.println("Conta origem e destino não podem ser iguais.");
            return;
        }

        banco.transferir(origem, destino, valor);
    }

    private static void consultarSaldo() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        banco.consultarSaldo(numero);
    }

    private static void aplicarRendimento() {
        System.out.print("Digite a taxa de rendimento (%): ");
        double taxa = scanner.nextDouble();
        scanner.nextLine();

        if (taxa <= 0) {
            System.out.println("Taxa de rendimento deve ser positiva.");
            return;
        }

        banco.aplicarRendimentoPoupancas(taxa);
    }

    private static void listarContas() {
        List<Conta> contas = banco.listarContas();
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            System.out.println("\n=== LISTA DE CONTAS (Ordenada por Saldo Decrescente) ===");
            for (int i = 0; i < contas.size(); i++) {
                Conta conta = contas.get(i);
                System.out.printf("%d. Tipo: %s | Número: %d | Cliente: %s | Saldo: R$ %.2f%n",
                        (i + 1), conta.getTipo(), conta.getNumero(),
                        conta.getCliente().getNome(), conta.getSaldo());
            }
            System.out.println("=========================================================");
        }
    }

    private static void exibirRelatorio() {
        banco.gerarRelatorioConsolidacao();
    }
}
