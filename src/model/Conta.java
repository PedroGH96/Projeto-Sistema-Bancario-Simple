package model;

public abstract class Conta {
    protected int numero;
    protected Cliente cliente;
    protected double saldo;
    protected String tipo;

    public Conta(Cliente cliente, double saldoInicial) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = saldoInicial;
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTipo() {
        return tipo;
    }

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
                numero, cliente.getNome(), saldo, tipo);
    }
}
