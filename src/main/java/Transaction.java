import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        //Identificador da transação
        String transactionId, //coluna[0]
        String accountId, //coluna[1]
        BigDecimal amount, //coluna[2]
        LocalDateTime timestamp,//coluna[3]
        //Tipo da transação (débito ou crédito)
        String type, //coluna[4]
        //Canal em que foi feito
        String channel, //coluna[9]
        //Profissão
        String occupation, // coluna[11]
        //Quantidade de tentaivas
        int loginAttempts, //coluna[13]
        //Saldo
        BigDecimal balance //coluna[14]
) {}
