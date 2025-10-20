package model;

public class ContaPoupanca extends Conta{
    public ContaPoupanca(Cliente cliente, double saldoInicial) {
        super(cliente, saldoInicial);
    }

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
