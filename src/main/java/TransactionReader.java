import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class TransactionReader {
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static List<Transaction> lerArquivo(String caminhoDoArquivo) {
        try (Stream<String> linhasDoArquivo = Files.lines(Path.of(caminhoDoArquivo))) {
            return linhasDoArquivo.skip(1)
                    .map(linha -> linha.split(","))
                    .map(coluna -> new Transaction(
                            coluna[0],
                            coluna[1],
                            new BigDecimal(coluna[2]),
                            LocalDateTime.parse(coluna[3], FORMATADOR),
                            coluna[4],
                            coluna[9],
                            coluna[11],
                            Integer.parseInt(coluna[13]),
                            new BigDecimal(coluna[14])
                    )).toList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar ler o arquivo CSV: " + e.getMessage());
        }
    }

}
