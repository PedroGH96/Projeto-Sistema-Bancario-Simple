package service;

import model.Conta;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioServico {

    public void gerarRelatorioConsolidacao(BancoServico bancoServico) {
        List<Conta> contas = bancoServico.getContas();

        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada para gerar relatório.");
            return;
        }

        Map<String, List<Conta>> contasPorTipo = contas.stream()
                .collect(Collectors.groupingBy(Conta::getTipo));

        System.out.println("\n=== RELATÓRIO DE CONSOLIDAÇÃO ===");

        for (Map.Entry<String, List<Conta>> entry : contasPorTipo.entrySet()) {
            String tipo = entry.getKey();
            List<Conta> contasDoTipo = entry.getValue();
            double saldoTotalTipo = contasDoTipo.stream()
                    .mapToDouble(Conta::getSaldo)
                    .sum();

            System.out.printf("Tipo: %s | Quantidade: %d | Saldo Total: R$ %.2f%n",
                    tipo, contasDoTipo.size(), saldoTotalTipo);
        }

        double saldoTotalBanco = contas.stream()
                .mapToDouble(Conta::getSaldo)
                .sum();

        System.out.printf("\nTOTAL GERAL | Quantidade: %d | Saldo Total: R$ %.2f%n",
                contas.size(), saldoTotalBanco);
        System.out.println("==================================\n");
    }
}
