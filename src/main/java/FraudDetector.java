import java.time.LocalTime;
import java.util.List;

public class FraudDetector {
    private static final LocalTime HORA_LIMITE = LocalTime.of(18, 0);

    public static List<Transaction> buscarTransacoesSuspeitas(List<Transaction> transacoes) {
        return transacoes.stream()
                .filter(t -> t.loginAttempts() > 3 || !t.timestamp().toLocalTime().isBefore(HORA_LIMITE))
                .toList();
    }
}
