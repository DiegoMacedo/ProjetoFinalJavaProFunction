import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        String transactionId, //col[0]
        String accountId, //co[1]
        BigDecimal amount, //col[2]
        LocalDateTime timestamp,//col[3]
        String type, //col[4]
        String channel, //col[9]
        String occupation, // col[11]
        int loginAttempts, //col[13]
        BigDecimal balance //col[14]
) {}
