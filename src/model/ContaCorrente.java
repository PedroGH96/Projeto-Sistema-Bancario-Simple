package model;

public class ContaCorrente extends Conta{
    public ContaCorrente(int numeroConta, Cliente cliente, double saldoInicial) {
        super(numeroConta, cliente, saldoInicial);
    }

    @Override
    public String getTipo() {
        return "Conta Corrente";
    }
}
