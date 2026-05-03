package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import Exception.*;


public class SavingsAccount extends Account {
    private LocalDateTime lastInterestApply; // data da ultima vez da aplicação de juros
    private static final BigDecimal INTEREST_RATE = new BigDecimal("1.005");

    public SavingsAccount(String ownerCpf) {
        super(ownerCpf);
        this.lastInterestApply = this.creationTime;
    }

    @Override
    public void withdraw(BigDecimal value) throws InvalidAmountException {
        if (value.compareTo(BigDecimal.ZERO) <= 0
                || value.compareTo(balance) > 0)
            throw new InvalidAmountException("Valor inválido");


        balance = balance.subtract(value).setScale(2, RoundingMode.HALF_UP);
    }

    public boolean isTimeToApplyInterest() {
        return !lastInterestApply.plusMonths(1).isAfter(LocalDateTime.now());
    }

    public boolean applyInterest(){
        if(!isTimeToApplyInterest()) return false;

        this.balance = this.balance.multiply(INTEREST_RATE).setScale(2, RoundingMode.HALF_UP); //0,5% de juros
        lastInterestApply = LocalDateTime.now();
        return true;
    }

    @Override
    public String getAccountType() {
        return "Conta Poupança";
    }
}
