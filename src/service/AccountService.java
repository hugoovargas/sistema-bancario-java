package service;

import model.*;
import Exception.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountService {
    private final Map<String, Account> accountMap;


    public AccountService(){
        this.accountMap = new HashMap<>();
    }


    public Account addAccount(String cpf, TypeAccount type) {
        Account account;


        if(type == TypeAccount.CHECKING){
            account = new CheckingAccount(cpf);
        }else{
            account = new SavingsAccount(cpf);
        }
        accountMap.put(account.getId(), account);
        return account;
    }


    public List<Account> getAccountsByClient(String cpf) throws InvalidCpfException {
        if(cpf == null) throw new InvalidCpfException("CPF inválido");
        return accountMap.values()
                .stream()
                .filter(a -> a.getClientId().equals(cpf))
                .toList();
    }


    public Account getAccountOfClient(String cpf, String accountId)
            throws AccountNotFoundException {


        Account account = get(accountId);


        if (!account.getClientId().equals(cpf)) {
            throw new AccountNotFoundException("Conta não pertence ao cliente");
        }


        return account;
    }


    public Account get(String id) throws AccountNotFoundException {
        Account account = accountMap.get(id);


        if (account == null) {
            throw new AccountNotFoundException("Conta não encontrada");
        }


        return account;
    }


    public BigDecimal getAccountBalance(String id) throws AccountNotFoundException {
        Account account = get(id);
        return account.getBalance();
    }
}
