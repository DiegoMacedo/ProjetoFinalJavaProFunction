import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ReportWriter {
    public static void exportarTransacoesSuspeitas(List<Transaction> transacoesSuspeitas, String nomeDoArquivo) {
        try (PrintWriter escreverNoArquivo = new PrintWriter(nomeDoArquivo, StandardCharsets.UTF_8)) {
            escreverNoArquivo.println("transaction_id, timestamp, loginAttempts, amount");
            transacoesSuspeitas.forEach(t -> escreverNoArquivo.printf("%s,%s,%d,%.2f%n",
                    t.transactionId(), t.timestamp(), t.loginAttempts(), t.amount()));
            System.out.println("Arquivo " + nomeDoArquivo + " gerado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao tentar exportar dos dados: " + e.getMessage());
        }
    }

    public static void gerarRelatorioEstatistico(Map<String, Double> mediaSaldoPorProfissao, Map<String, Long> volumePorCanalDeAtendimento, String nomeDoArquivo) {
        try (PrintWriter escreverNoArquivo = new PrintWriter(nomeDoArquivo, StandardCharsets.UTF_8)) {
            escreverNoArquivo.println("=================================================");
            escreverNoArquivo.println("       RELATÓRIO ESTATISTICO DE TRANSAÇÕES");
            escreverNoArquivo.println("=================================================");

            escreverNoArquivo.println("\n--- MÉDIA DE SALDO POR PROFISSÃO ---");
            mediaSaldoPorProfissao.forEach((profissao, mediaDeSaldo) ->
                    escreverNoArquivo.printf("%s: R$ %.2f%n", profissao, mediaDeSaldo));

            escreverNoArquivo.println("\n--- VOLUME POR CANAL ---");
            volumePorCanalDeAtendimento.forEach((canalDeAtendimento, total) ->
                    escreverNoArquivo.printf("%s: %d transações%n", canalDeAtendimento, total));

            escreverNoArquivo.println("\n==============================================");
            System.out.println("Relatório estatístico gerado: " + nomeDoArquivo);

        } catch (Exception e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
