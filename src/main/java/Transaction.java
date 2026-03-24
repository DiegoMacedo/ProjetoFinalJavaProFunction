import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        //Identificador da transação
        String transactionId, //col[0]

        String accountId, //co[1]
        BigDecimal amount, //col[2]
        LocalDateTime timestamp,//col[3]
        //Tipo da transação (débito ou crédito)
        String type, //col[4]
        //Canal em que foi feito
        String channel, //col[9]
        //Profissão
        String occupation, // col[11]
        //Quantidade de tentaivas
        int loginAttempts, //col[13]
        //Saldo
        BigDecimal balance //col[14]
) {}
