package model;

public class ContaCorrente extends Conta{
    public ContaCorrente(Cliente cliente, double saldoInicial) {
        super(cliente, saldoInicial);
    }

    @Override
    public String getTipo() {
        return "Conta Corrente";
    }
}
