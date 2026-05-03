package service;




import model.*;
import Exception.*;


import java.math.BigDecimal;
import java.util.List;




public class BankService {
    private final ClientService clientService;
    private final AccountService accountService;
    private final TransactionService transactionService;


    public BankService() {
        this.clientService = new ClientService();
        this.accountService = new AccountService();
        this.transactionService = new TransactionService(accountService);
    }


    public void createClient(String name, String cpf, String email) throws CpfAlreadyExistsException, InvalidCpfException {
        clientService.addClient(name, cpf, email);
    }


    public Account createAccount(String cpf, TypeAccount type) throws ClientNotFoundException {
        return accountService.addAccount(cpf, type);
    }


    public Client getClient(String cpf) throws ClientNotFoundException {
        return clientService.get(cpf);
    }


    public Account getAccount(String id) throws AccountNotFoundException {
        return accountService.get(id);
    }


    public List<Account> getAccountsByClient(String cpf) throws InvalidCpfException, AccountNotFoundException {
        if(accountService.getAccountsByClient(cpf).isEmpty()){
            throw new AccountNotFoundException("Cliente não possui nenhuma conta");
        }
        return accountService.getAccountsByClient(cpf);
    }


    public void deposit(String id, BigDecimal value) throws InvalidAmountException, InvalidTransferException,
            AccountNotFoundException {
        transactionService.deposit(id, value);
    }


    public void withdraw(String id, BigDecimal value) throws InvalidAmountException, InsufficientBalanceException,
            InvalidTransferException, AccountNotFoundException {
        transactionService.withdraw(id, value);
    }


    public void transfer(String fromId, String toId, BigDecimal value) throws InvalidAmountException, InsufficientBalanceException,
            InvalidTransferException, AccountNotFoundException {


        transactionService.transfer(fromId, toId, value);
    }


    public Account getAccountOfClient(String cpf, String accountId)
            throws AccountNotFoundException {


        return accountService.getAccountOfClient(cpf, accountId);
    }


    public BigDecimal getAccountBalance(String id) throws AccountNotFoundException {
        return accountService.getAccountBalance(id);
    }
}
