package model;

public class ContaPoupanca extends Conta{
    public ContaPoupanca(int numeroConta, Cliente cliente, double saldoInicial) {
        super(numeroConta, cliente, saldoInicial);
    }

    @Override
    public void aplicarRendimento(double percentual) {
        if (percentual > 0) {
            saldo += saldo * (percentual / 100);
        }
    }

    @Override
    public String getTipo() {
        return "Conta Poupança";
    }
}
