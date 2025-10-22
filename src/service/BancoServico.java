package service;

import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BancoServico {
    private List<Cliente> clientes;
    private List<Conta> contas;

    public BancoServico() {
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
    }

    public Cliente cadastrarCliente(String nome, String cpf) {
        if (clientes.stream().anyMatch(c -> c.getCpf().equals(cpf))) {
            System.out.println("Cliente com CPF " + cpf + " já existe.");
            return null;
        }

        Cliente novoCliente = new Cliente(nome, cpf);

        clientes.add(novoCliente);
        System.out.println("Cliente " + nome + " cadastrado com sucesso.");
        return novoCliente;
    }

    public Conta cadastrarConta(Cliente cliente, String tipoConta, double saldoInicial) {
        if (cliente == null) {
            System.out.println("Cliente não pode ser nulo para cadastrar conta.");
            return null;
        }

        Conta novaConta;

        if ("corrente".equalsIgnoreCase(tipoConta)) {
            novaConta = new ContaCorrente(cliente, saldoInicial);
        } else if ("poupanca".equalsIgnoreCase(tipoConta)) {
            novaConta = new ContaPoupanca(cliente, saldoInicial);
        } else {
            System.out.println("Tipo de conta inválido. Use 'corrente' ou 'poupanca'.");
            return null;
        }

        contas.add(novaConta);

        System.out.println("Conta " + tipoConta + " para " + cliente.getNome() + " criada com sucesso. Número: " + novaConta.getNumero());
        return novaConta;
    }

    public Optional<Conta> buscarConta(int numeroConta) {
        return contas.stream().filter(c -> c.getNumero() == numeroConta).findFirst();
    }

    public boolean depositar(int numeroConta, double valor) {
        Optional<Conta> contaOpt = buscarConta(numeroConta);
        if (contaOpt.isPresent()) {
            contaOpt.get().depositar(valor);
            System.out.println("Depósito de " + valor + " realizado na conta " + numeroConta);
            return true;
        }
        System.out.println("Conta " + numeroConta + " não encontrada.");
        return false;
    }

    public boolean sacar(int numeroConta, double valor) {
        Optional<Conta> contaOpt = buscarConta(numeroConta);
        if (contaOpt.isPresent()) {
            if (contaOpt.get().sacar(valor)) {
                System.out.println("Saque de " + valor + " realizado da conta " + numeroConta);
                return true;
            } else {
                System.out.println("Saldo insuficiente na conta " + numeroConta);
                return false;
            }
        }
        System.out.println("Conta " + numeroConta + " não encontrada.");
        return false;
    }

    public boolean transferir(int contaOrigem, int contaDestino, double valor) {
        Optional<Conta> origemOpt = buscarConta(contaOrigem);
        Optional<Conta> destinoOpt = buscarConta(contaDestino);

        if (origemOpt.isPresent() && destinoOpt.isPresent()) {
            if (origemOpt.get().transferir(destinoOpt.get(), valor)) {
                System.out.println("Transferência de " + valor + " da conta " + contaOrigem + " para a conta " + contaDestino + " realizada com sucesso.");
                return true;
            } else {
                System.out.println("Falha na transferência: saldo insuficiente ou valor inválido.");
                return false;
            }
        }
        System.out.println("Conta de origem ou destino não encontrada.");
        return false;
    }

    public Double consultarSaldo(int numeroConta) {
        Conta conta = buscarContaPorNumero(numeroConta);
        return conta != null ? conta.getSaldo() : null;
    }
  
    public void aplicarRendimentoPoupancas(double percentual) {
        for (Conta conta : contas) {
            if (conta.getTipo().equals("POUPANCA")) {
                conta.aplicarRendimento(percentual);
            }
        }
    }

    public List<Conta> listarContas() {
        return contas.stream()
                .sorted((c1, c2) -> Double.compare(c2.getSaldo(), c1.getSaldo()))
                .collect(Collectors.toList());
    }

    private Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    private Conta buscarContaPorNumero(int numero) {
        return contas.stream()
                .filter(c -> c.getNumero() == numero)
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public List<Conta> getContas() {
        return new ArrayList<>(contas);
    }
}
