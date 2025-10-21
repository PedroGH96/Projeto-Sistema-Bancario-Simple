package servico;

import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BancoServico {
    private List<Cliente> clientes;
    private List<Conta> contas;
    private int proximoNumeroConta;

    public BancoService() {
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.proximoNumeroConta = 1;
    }

    public boolean cadastrarCliente(String nome, String cpf) {
        Cliente novoCliente = new Cliente(nome, cpf);

        if (clientes.contains(novoCliente)) {
            return false;
        }
        
        clientes.add(novoCliente);
        return true;
    }

    public boolean cadastrarContaCorrente(String cpf, double saldoInicial) {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) return false;

        int numeroConta = proximoNumeroConta++;
        Conta novaConta = new ContaCorrente(numeroConta, cliente, saldoInicial);
        contas.add(novaConta);
        return true;
    }

    public boolean cadastrarContaPoupanca(String cpf, double saldoInicial) {
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) return false;

        int numeroConta = proximoNumeroConta++;
        Conta novaConta = new ContaPoupanca(numeroConta, cliente, saldoInicial);
        contas.add(novaConta);
        return true;
    }
  
    public boolean depositar(int numeroConta, double valor) {
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta != null && valor > 0) {
            conta.depositar(valor);
            return true;
        }
        return false;
    }

    public boolean sacar(int numeroConta, double valor) {
        Conta conta = buscarContaPorNumero(numeroConta);
        return conta != null && conta.sacar(valor);
    }

    public boolean transferir(int numeroContaOrigem, int numeroContaDestino, double valor) {
        Conta origem = buscarContaPorNumero(numeroContaOrigem);
        Conta destino = buscarContaPorNumero(numeroContaDestino);
        
        if (origem != null && destino != null && valor > 0) {
            return origem.transferir(destino, valor);
        }
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
