import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ReportWriter {
    public static void exportarSuspeitas(List<Transaction> suspeitas, String nomeDoArquivo) {
        try (PrintWriter escreverNoArquivo = new PrintWriter(nomeDoArquivo, StandardCharsets.UTF_8)) {
            escreverNoArquivo.println("transaction_id, timestamp, loginAttempts, amount");
            suspeitas.forEach(t -> escreverNoArquivo.printf("%s,%s,%d,%.2f%n",
                    t.transactionId(), t.timestamp(), t.loginAttempts(), t.amount()));
            System.out.println("Arquivo " + nomeDoArquivo + " gerado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao tentar exportar dos dados: " + e.getMessage());
        }
    }

    public static void gerarRelatorioEstatistico(Map<String, Double> medias, Map<String, Long> volumes, String nomeDoArquivo) {
        try (PrintWriter escreverNoArquivo = new PrintWriter(nomeDoArquivo, StandardCharsets.UTF_8)) {
            escreverNoArquivo.println("=================================================");
            escreverNoArquivo.println("       RELATÓRIO ESTATISTICO DE TRANSAÇÕES");
            escreverNoArquivo.println("=================================================");

            escreverNoArquivo.println("\n--- MÉDIA DE SALDO POR PROFISSÃO ---");
            medias.forEach((prof, media) ->
                    escreverNoArquivo.printf("%s: R$ %.2f%n", prof, media));

            escreverNoArquivo.println("\n--- VOLUME POR CANAL ---");
            volumes.forEach((canal, total) ->
                    escreverNoArquivo.printf("%s: %d transações%n", canal, total));

            escreverNoArquivo.println("\n==============================================");
            System.out.println("Relatório estatístico gerado: " + nomeDoArquivo);

        } catch (Exception e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
