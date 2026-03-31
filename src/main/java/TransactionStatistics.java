import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionStatistics {
    public static BigDecimal calcularValorLiquido(List<Transaction> transacoes) {
        return transacoes.stream()
                .map(t -> "Debit".equalsIgnoreCase(t.type()) ? t.amount().negate() : t.amount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static List<Transaction> obterTop10(List<Transaction> transacoes) {
        return transacoes.stream()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(10)
                .toList();
    }

    public static Map<String, Double> mediaSaldoPorProfissao(List<Transaction> transacoes) {
        return transacoes.stream()
                .collect(Collectors.groupingBy(Transaction::occupation,
                        Collectors.averagingDouble(t -> t.balance().doubleValue())));
    }

    public static Map<String, Long> volumePorCanal(List<Transaction> transacoes) {
        return transacoes.stream()
                .collect(Collectors.groupingBy(
                        Transaction::channel,
                        Collectors.counting()
                ));
    }
}
