package model;

import java.time.LocalDateTime;


public class Transaction {
    private final TypeTransaction type;
    private final double amount;
    LocalDateTime dateTime;
    private final Account source;
    private final Account destination;


    public Transaction(TypeTransaction type, double amount, Account source, Account destination) {
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.source = source;
        this.destination = destination;
    }


    public TypeTransaction getType() {
        return type;
    }


    public double getAmount() {
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

