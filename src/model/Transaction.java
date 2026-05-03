package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import Exception.*;

public class Transaction {
    private final TypeTransaction type;
    private final BigDecimal amount;
    private final LocalDateTime dateTime;
    private final Account source;
    private final Account destination;

    public Transaction(TypeTransaction type, BigDecimal amount, Account source, Account destination) throws InvalidTransferException {
        if(source == null && destination == null){
            throw new InvalidTransferException("transação sem origem e destino");
        }
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.source = source;
        this.destination = destination;
    }

    public TypeTransaction getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Account getSource() {
        return source;
    }

    public Account getDestination() {
        return destination;
    }
}
