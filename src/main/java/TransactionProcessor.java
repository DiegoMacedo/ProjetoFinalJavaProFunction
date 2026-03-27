import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TransactionProcessor {
    static void main(String[] args) {
        System.out.println("Iniciando o processamento assíncrono...");
        CompletableFuture<List<Transaction>> tarefaLeitura = CompletableFuture.supplyAsync(() ->
                TransactionReader.lerArquivo("bank_transactions_data_2.csv")
        );

        CompletableFuture<Void> processamentoCompleto = tarefaLeitura.thenCompose(todas -> {

            CompletableFuture<Void> estatisticass = CompletableFuture.runAsync(() -> {
                var total = TransctionStatistics.calcularValorLiquido(todas);
                System.out.println("Cálculo de Saldo Líquido Conclído: " + total);
            });

            CompletableFuture<Void> fraudes = CompletableFuture.runAsync(() -> {
                var suspeitas = FraudDetector.buscarSuspeitas(todas);
                ReportWriter.exportarSuspeitas(suspeitas, "transacoes_suspeitas.csv");
            });
            return CompletableFuture.allOf(estatisticass, fraudes);
        });
        processamentoCompleto.join();
        System.out.println("Todos os relatórios foram gerados com sucesso!");

    }
}
