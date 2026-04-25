package service;

import model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankService {
    private final Map<String, Client> clientMap;
    private final Map<String, Account> accountMap;


    public BankService() {
        this.clientMap = new HashMap<>();
        this.accountMap = new HashMap<>();
    }


    public void addClient(String name, String cpf, String email){
        if(!isCPFValid(cpf)) throw new IllegalArgumentException("CPF Inválido");


        if(clientMap.containsKey(cpf)){
            throw new IllegalArgumentException("CPF JÁ CADASTRADO");
        }
        Client client = new Client(name, cpf, email);
        clientMap.put(cpf, client);
    }


    public Account addAccount(String cpf, TypeAccount type){
        Client client = clientMap.get(cpf);
        if(client == null) throw new IllegalArgumentException("Cliente não encontrado");


        Account account;


        if(type == TypeAccount.CHECKING){
            account = new CheckingAccount(cpf);
        }else{
            account = new SavingsAccount(cpf);
        }
        client.addAccount(account);
        accountMap.put(account.getId(), account);
        return account;
    }


    public List<Account> getAccountsByCpf(String cpf) {
        Client client = clientMap.get(cpf);


        if (client == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }


        return List.copyOf(client.getAccounts());
    }


    public boolean deposit(String id, double value){
        Account account = getAccountsById(id);


        if(account == null) throw new IllegalArgumentException("Conta não encontrada");


        return account.deposit(value);
    }


    public boolean withdraw(String id, double value){
        Account account = getAccountsById(id);


        if(account == null) throw new IllegalArgumentException("Conta não encontrada");


        return account.withdraw(value);
    }


    private Account getAccountsById(String id){
        return accountMap.get(id);
    }


    public double getAccountBalance(String accountId) {
        Account account = accountMap.get(accountId);


        if (account == null)
            throw new IllegalArgumentException("Conta não encontrada");


        return account.getBalance();
    }


    private boolean isCPFValid(String cpf) {
        if (cpf == null) return false;


        cpf = cpf.replaceAll("[^0-9]", "");


        if (cpf.length() != 11) return false;


        // Bloqueia CPFs com todos os números iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;


        try {
            int sum = 0;


            // 1º dígito
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * (10 - i);
            }


            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) firstDigit = 0;


            if (firstDigit != (cpf.charAt(9) - '0')) return false;


            // 2º dígito
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * (11 - i);
            }


            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) secondDigit = 0;


            return secondDigit == (cpf.charAt(10) - '0');


        } catch (Exception e) {
            return false;
        }
    }
}

