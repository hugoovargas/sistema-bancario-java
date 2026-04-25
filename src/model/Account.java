package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class Account {
    private static int idCounter = 1;
    protected final String id;
    private final String ownerCpf;
    private String branch;
    protected double balance;
    protected LocalDateTime creationTime;
    protected List<Transaction> transactionHistory;


    public Account(String ownerCpf) {
        this.id = idGenerator();
        this.ownerCpf = ownerCpf;
        this.branch = "0001";
        this.balance = 0;
        this.creationTime = LocalDateTime.now();
        this.transactionHistory = new ArrayList<>();
    }
    public boolean deposit(double value){
        if(value <= 0) return false;
        this.balance += value;
        addTransaction(new Transaction(TypeTransaction.DEPOSIT, value, null, this));
        return true;
    }
    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
    public abstract boolean withdraw(double value);
    private String idGenerator() {
        return String.format("%06d", idCounter++); // ex: 000001, 000002
    }
    public String getId() {
        return id;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public LocalDateTime getCreationTime() {
        return creationTime;
    }
    public String getOwnerCpf() {
        return ownerCpf;
    }
    @Override
    public String toString() {
        return getAccountType() + " (ID: " + this.id + ")";
    }
    public abstract String getAccountType();
}

