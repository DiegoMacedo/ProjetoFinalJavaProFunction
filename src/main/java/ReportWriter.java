import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
}
