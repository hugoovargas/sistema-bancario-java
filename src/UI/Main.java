package UI;

import model.*;
import service.BankService;
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


                // =======================
                // CRIAR CLIENTE
                // =======================
                case 1:
                    System.out.print("Nome completo: ");
                    String name = scanner.nextLine();


                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();


                    System.out.print("Email: ");
                    String email = scanner.nextLine();


                    try {
                        bankService.addClient(name, cpf, email);
                        System.out.println("Cliente cadastrado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =======================
                // CRIAR CONTA
                // =======================
                case 2:
                    System.out.print("CPF do cliente: ");
                    String cpfAccount = scanner.nextLine();


                    System.out.println("Tipo da conta:");
                    System.out.println("1 - Conta Corrente");
                    System.out.println("2 - Conta Poupança");
                    System.out.print("Escolha: ");


                    int typeOption = scanner.nextInt();
                    scanner.nextLine();


                    if(typeOption != 1 && typeOption != 2){
                        System.out.println("Opção inválida");
                        break;
                    }


                    TypeAccount type = (typeOption == 1)
                            ? TypeAccount.CHECKING
                            : TypeAccount.SAVING;


                    try {
                        Account account = bankService.addAccount(cpfAccount, type);
                        System.out.println("Conta criada com sucesso! ID: " + account.getId());
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                // =======================
                // ACESSAR CONTA
                // =======================
                case 3:
                    System.out.print("Digite seu CPF: ");
                    String cpfAccess = scanner.nextLine();


                    try {
                        List<Account> accounts = bankService.getAccountsByCpf(cpfAccess);


                        if (accounts.isEmpty()) {
                            System.out.println("Nenhuma conta encontrada.");
                            break;
                        }


                        System.out.println("\nSuas contas:");
                        for (int i = 0; i < accounts.size(); i++) {
                            Account acc = accounts.get(i);
                            System.out.println((i + 1) + " - " + acc.getAccountType()
                                    + " | ID: " + acc.getId()
                                    + " | Saldo: R$ " + acc.getBalance());
                        }


                        System.out.print("\nEscolha a conta: ");
                        int accChoice = scanner.nextInt();
                        scanner.nextLine();


                        if (accChoice < 1 || accChoice > accounts.size()) {
                            System.out.println("Conta inválida");
                            break;
                        }


                        Account selected = accounts.get(accChoice - 1);


                        accountMenu(scanner, bankService, selected.getId());


                    } catch (Exception e) {
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


    // =======================
    // MENU DA CONTA
    // =======================
    private static void accountMenu(Scanner scanner, BankService bankService, String accountId) {


        while (true) {
            System.out.println("\n===== CONTA " + accountId + " =====");
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Ver saldo");
            System.out.println("0 - Voltar");


            System.out.print("Escolha: ");
            int option = scanner.nextInt();
            scanner.nextLine();


            switch (option) {


                case 1:
                    System.out.print("Valor: ");
                    double deposit = scanner.nextDouble();
                    scanner.nextLine();


                    if(bankService.deposit(accountId, deposit)) System.out.println("Depósito realizado!");
                    else System.out.println("Não foi possível realizar depósito");
                    break;


                case 2:
                    System.out.print("Valor: ");
                    double withdraw = scanner.nextDouble();
                    scanner.nextLine();


                    boolean success = bankService.withdraw(accountId, withdraw);


                    if (success) {
                        System.out.println("Saque realizado!");
                    } else {
                        System.out.println("Valor inválido ou Saldo insuficiente");
                    }
                    break;


                case 3:
                    System.out.println("Saldo: R$ " +
                            bankService.getAccountBalance(accountId));
                    break;


                case 0:
                    return;


                default:
                    System.out.println("Opção inválida");
            }
        }
    }
}

