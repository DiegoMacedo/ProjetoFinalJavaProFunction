import java.util.concurrent.CompletableFuture;

public class TransactionProcessor {
    public static void main(String[] args) {
        System.out.println("Iniciando o processamento assíncrono...");
        try {
            CompletableFuture.supplyAsync(() -> TransactionReader.lerArquivo("bank_transactions_data_2.csv"))
                    .thenAccept(todas -> {

                        var tarefaGeraRelatorio = CompletableFuture.runAsync(() -> {
                            var mediaSaldoPorProfissao = TransactionStatistics.mediaSaldoPorProfissao(todas);
                            var volumePorCanalDeAtendimento = TransactionStatistics.volumePorCanal(todas);
                            ReportWriter.gerarRelatorioEstatistico(mediaSaldoPorProfissao, volumePorCanalDeAtendimento, "relatorio_estatistico.txt");
                        });

                        var tarefaGeraArquivoFralde = CompletableFuture.runAsync(() -> {
                            var transacoesSuspeitas = FraudDetector.buscarTransacoesSuspeitas(todas);
                            ReportWriter.exportarTransacoesSuspeitas(transacoesSuspeitas, "transacoes_suspeitas.csv");
                        });

                        System.out.println("\n==================================================");
                        System.out.println("   RELATÓRIO DE TRANSAÇÕES BANCÁRIAS - SUMÁRIO");
                        System.out.println("==================================================");
                        System.out.printf("Total de transações processadas: %, d%n", todas.size());
                        System.out.printf("Valor total movimentado: R$%,.2f%n", TransactionStatistics.calcularValorLiquido(todas));

                        System.out.println("--------------------------------------------------");
                        System.out.println("TOP 10 MAIORES TRANSAÇÕES:");
                        TransactionStatistics.obterTop10(todas).forEach(t ->
                                System.out.printf("ID: %s | Valor: R$%,.2f | Conta: %s%n",
                                        t.transactionId(), t.amount(), t.accountId()));

                        CompletableFuture.allOf(tarefaGeraRelatorio, tarefaGeraArquivoFralde).join();

                    }).join();

            System.out.println("\nProcesso finalizado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro fatal no processamento: " + e.getCause().getMessage());
        }
    }
}