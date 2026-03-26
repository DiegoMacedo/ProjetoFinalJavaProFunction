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
        List<Transaction> todas = TransactionReader.lerArquivo("bank_transactions_data_2.csv");

        System.out.println("Total movimentado "+ TransctionStatistics.calcularValorLiquido(todas));

        List<Transaction> transacoesSuspeitas = FraudDetector.buscarSuspeitas(todas);

        ReportWriter.exportarSuspeitas(transacoesSuspeitas, "trasacoes_suspeitas.csv");
    }
}

