import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
                            new BigDecimal(col[14])
                    ))
                    .limit(10)
                    .toList();
            System.out.println("Sucesso! Total de transações mapeadas: " + transacoes.size());


        } catch (IOException e) {
            System.err.println("Erro ao tentar processar o arquvo: " + e.getMessage());
        }
    }
}

