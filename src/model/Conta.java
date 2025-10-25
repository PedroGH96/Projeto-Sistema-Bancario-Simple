package model;

public abstract class Conta {
    protected int numeroConta;
    protected Cliente cliente;
    protected double saldo;

    public Conta(int numeroConta, Cliente cliente, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.cliente = cliente;
        this.saldo = saldoInicial;
    }

    public int getNumero() {
        return numeroConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public abstract String getTipo();

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public boolean transferir(Conta destino, double valor) {
        if (this.sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public void aplicarRendimento(double percentual) {
    }

    @Override
    public String toString() {
        return String.format("Conta{numero=%d, cliente=%s, saldo=%.2f, tipo='%s'}",
                numeroConta, cliente.getNome(), saldo, getTipo());
    }
}
