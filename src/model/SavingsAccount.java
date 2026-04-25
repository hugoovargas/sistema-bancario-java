package model;

import java.time.LocalDateTime;


public class SavingsAccount extends Account {
    private LocalDateTime lastInterestApply; // data da ultima vez da aplicação de juros


    public SavingsAccount(String ownerCpf) {
        super(ownerCpf);
        this.lastInterestApply = this.creationTime;
    }


    @Override
    public boolean withdraw(double value) {
        if(value <= 0 || value >  this.balance) return false;


        this.balance -= value;
        addTransaction(new Transaction(TypeTransaction.WITHDRAW, value, this, null));
        return true;
    }


    public boolean isTimeToApplyInterest() {
        return !lastInterestApply.plusMonths(1).isAfter(LocalDateTime.now());
    }


    public boolean applyInterest(){
        if(!isTimeToApplyInterest()) return false;


        this.balance *= 1.005; // 0,5% de juros
        lastInterestApply = LocalDateTime.now();
        return true;
    }


    @Override
    public String getAccountType() {
        return "Conta Poupança";
    }
}

