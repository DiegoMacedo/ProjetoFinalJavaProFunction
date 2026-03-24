import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionProcessor {
    static void main(String[] args) {
        Path caminhoDoArquivo = Path.of("bank_transactions_data_2.csv");
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Stream<String> linhas = Files.lines(caminhoDoArquivo)) {
            List<Transaction> transacoes = linhas
                    .skip(1)
                    .map(linha -> linha.split(","))
                    .map(col -> new Transaction(
                            col[0],
                            col[1],
                            new BigDecimal(col[2]),
                            LocalDateTime.parse(col[3], formatador),
                            col[4],
                            col[9],
                            col[11],
                            Integer.parseInt(col[13]),
                            new BigDecimal(col[14])))
                    .limit(10)
                    .toList();
            System.out.println("Sucesso! Total de transações mapeadas: " + transacoes.size());

            BigDecimal totalMovimento = transacoes.stream()
                    .map(t -> {
                        if ("Debit".equalsIgnoreCase(t.type())) {
                            return t.amount().negate();
                        }
                        return t.amount();
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println("Valor total movimentado: R$ " + totalMovimento);

            List<Transaction> top10Transacoes = transacoes.stream()
                    .sorted(Comparator.comparing(Transaction::amount).reversed())
                    .limit(10)
                    .toList();
            System.out.println("\n--- TOP 10 MAIORES TRANSAÇÕES ---");
            top10Transacoes.forEach(t ->
                    System.out.printf("ID: %s | Valor: R$ %.2f | Data: %s%n",
                            t.transactionId(), t.amount(), t.timestamp()));

            Map<String, Double> mediaPorProfissao = transacoes.stream()
                    .collect(Collectors.groupingBy(
                            Transaction::occupation,
                            Collectors.averagingDouble(t -> t.balance().doubleValue())
                    ));
            System.out.println("\n --- SALDO MÉDIO POR PROFISSÃO ---");
            mediaPorProfissao.forEach((profissao, media) ->
                    System.out.printf("%s: R$ %.2f%n ", profissao, media)
            );

            Map<String, Long> totalDeTransacoesPorCanal = transacoes.stream()
                    .collect(Collectors.groupingBy(
                            Transaction::channel,
                            Collectors.counting()
                    ));
            System.out.println("\n --- TOTAL DE TRANSAÇÕES POR CANAL");
            totalDeTransacoesPorCanal.forEach((canal, total) ->
                    System.out.printf("%s: %d ", canal, total));

            LocalTime horaLimite = LocalTime.of(18,0);

            List<Transaction> transacoesSuspeitas = transacoes.stream()
                    .filter(t -> t.loginAttempts() > 3 || !t.timestamp().toLocalTime().isBefore(horaLimite))
                    .toList();
            System.out.println("\nSuspeitas: " + transacoesSuspeitas);


        } catch (IOException e) {
            System.err.println("Erro ao tentar processar o arquvo: " + e.getMessage());
        }
    }
}

