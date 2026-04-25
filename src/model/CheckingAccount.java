package model;

public class CheckingAccount extends Account{
    private final double withdrawLimit;


    public CheckingAccount(String ownerCpf) {
        super(ownerCpf);
        this.withdrawLimit = 1000;
    }


    @Override
    public boolean withdraw(double value) {
        if(value <= 0) return false;
        double available = balance + withdrawLimit;
        if(available < value) return false;


        balance -= value;
        addTransaction(new Transaction(TypeTransaction.WITHDRAW, value, this, null));
        return true;
    }


    @Override
    public String getAccountType() {
        return "Conta Corrente";
    }
}
