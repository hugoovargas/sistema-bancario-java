package UI;

import model.*;
import Exception.*;
import service.BankService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();

        while (true) {
            System.out.println("\n===== BANKLITE =====");
            System.out.println("1 - Criar cliente");
            System.out.println("2 - Criar conta");
            System.out.println("3 - Acessar conta");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                // CRIAR CLIENTE
                case 1:
                    System.out.print("Nome completo: ");
                    String name = scanner.nextLine();

                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    try {
                        bankService.createClient(name, cpf, email);
                        System.out.println("Cliente cadastrado com sucesso!");
                    } catch (CpfAlreadyExistsException | InvalidCpfException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                // CRIAR CONTA
                case 2:
                    System.out.print("CPF do cliente: ");
                    String cpfAccount = scanner.nextLine();


                    try {
                        bankService.getClient(cpfAccount);


                        System.out.println("Tipo da conta:");
                        System.out.println("1 - Conta Corrente");
                        System.out.println("2 - Conta Poupança");
                        System.out.print("Escolha: ");

                        int typeOption = scanner.nextInt();
                        scanner.nextLine();

                        if (typeOption != 1 && typeOption != 2) {
                            System.out.println("Opção inválida");
                            break;
                        }

                        TypeAccount type = (typeOption == 1)
                                ? TypeAccount.CHECKING
                                : TypeAccount.SAVING;

                        Account account = bankService.createAccount(cpfAccount, type);
                        System.out.println("Conta criada com sucesso! ID: " + account.getId());


                    } catch (ClientNotFoundException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }


                    break;

                // ACESSAR CONTA
                case 3:
                    System.out.print("Digite seu CPF: ");
                    String cpfClient = scanner.nextLine();


                    try {
                        List<Account> accounts = bankService.getAccountsByClient(cpfClient);


                        if(accounts.isEmpty()){
                            System.out.println("Cliente não possui contas");
                            break;
                        }


                        for (Account account : accounts) {
                            System.out.println(account);
                        }


                        System.out.println("Escolha o id da conta que você deseja acessar: ");
                        String id = scanner.nextLine();
                        bankService.getAccountOfClient(cpfClient, id);
                        accountMenu(scanner, bankService, id);
                    }catch (InvalidCpfException |
                            AccountNotFoundException e){
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    return;




                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    // MENU DA CONTA
    private static void accountMenu(Scanner scanner, BankService bankService, String accountId) {

        while (true) {
            System.out.println("\n===== CONTA " + accountId + " =====");
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Ver saldo");
            System.out.println("4 - Transferir");
            System.out.println("0 - Voltar");


            System.out.print("Escolha: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    System.out.print("Valor: ");
                    BigDecimal depositValue = new BigDecimal(scanner.nextLine());

                    try{
                        bankService.deposit(accountId, depositValue);
                        System.out.println("Depósito realizado!");
                    }
                    catch (IllegalArgumentException | InvalidAmountException |
                           InvalidTransferException | AccountNotFoundException e){ System.out.println("Erro: " + e.getMessage());}
                    break;

                case 2:
                    System.out.print("Valor: ");
                    BigDecimal withdrawValue = new BigDecimal(scanner.nextLine());


                    try{bankService.withdraw(accountId, withdrawValue);
                        System.out.println("Saque realizado!");}
                    catch (IllegalArgumentException | InvalidAmountException |
                           InsufficientBalanceException | InvalidTransferException |
                           AccountNotFoundException e){
                        System.out.println("Erro: " + e.getMessage());
                    }

                    break;

                case 3:
                    try {
                        System.out.println("Saldo: R$ " + bankService.getAccountBalance(accountId).toPlainString());
                    } catch (AccountNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 4:
                    System.out.println("Para quem vocẽ deseja fazer a transferẽncia? (id)");
                    String to = scanner.nextLine();

                    System.out.println("digite o valor que vocẽ deseja transferir: ");
                    BigDecimal value = new BigDecimal(scanner.nextLine());

                    try {
                        bankService.transfer(accountId, to, value);
                    }
                    catch (IllegalArgumentException | InvalidAmountException |
                           InsufficientBalanceException | InvalidTransferException |
                           AccountNotFoundException e){
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opção inválida");
            }
        }
    }
}
