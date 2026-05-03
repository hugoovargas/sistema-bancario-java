package service;

import model.Account;
import model.Transaction;
import model.TypeTransaction;
import Exception.*;


import java.math.BigDecimal;


public class TransactionService {
    private final AccountService accountService;


    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }


    public void deposit(String id, BigDecimal value) throws InvalidAmountException, InvalidTransferException,
            AccountNotFoundException {


        Account account = accountService.get(id);
        account.deposit(value);
        account.addTransaction(new Transaction(TypeTransaction.DEPOSIT, value, null, account));
    }




    public void withdraw(String id, BigDecimal value) throws InvalidAmountException, InsufficientBalanceException,
            InvalidTransferException, AccountNotFoundException {


        Account account = accountService.get(id);
        account.withdraw(value);
        account.addTransaction(new Transaction(TypeTransaction.WITHDRAW, value, account, null));
    }




    public void transfer(String fromId, String toId, BigDecimal value) throws InvalidAmountException , InsufficientBalanceException,
            InvalidTransferException, AccountNotFoundException {


        Account from = accountService.get(fromId);
        Account to = accountService.get(toId);


        from.withdraw(value);


        try{
            to.deposit(value);
            Transaction t = new Transaction(TypeTransaction.TRANSFER, value, from, to);
            from.addTransaction(t);
            to.addTransaction(t);
        }catch(InvalidAmountException e){
            from.deposit(value);
            throw e;
        }
    }
}
