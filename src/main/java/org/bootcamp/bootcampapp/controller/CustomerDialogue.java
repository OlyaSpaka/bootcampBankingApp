//package org.bootcamp.bootcampapp.controller;
//
//import org.bootcamp.bootcampapp.entity.AccountDataEntity;
//import org.bootcamp.bootcampapp.service.BankingAccountService ;
//import org.bootcamp.bootcampapp.service.Operations;
//import org.bootcamp.bootcampapp.service.Validator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Scanner;
//
//@Component
//public class CustomerDialogue {
//    private final Scanner sc = new Scanner(System.in);
//    private AccountDataEntity currentAccount;
//
//    @Autowired
//    private BankingAccountService bankingAccountService;
//    @Autowired
//    private Operations operations;
//
//    public void start() {
//        decideOldOrNewAccount();
//    }
//
//    void decideOldOrNewAccount() {
//        System.out.println(
//                "Choose one of the following operations: \n" +
//                        "1 - Start with a new account\n" +
//                        "2 - Continue working with an existing account\n" +
//                        "3 - Exit"
//        );
//
//        while (true) {
//            switch (sc.nextInt()) {
//                case 1:
//                    createAccount();
//                    break;
//                case 2:
//                    chooseExistingAccount();
//                    dialogue();
//                    break;
//                case 3:
//                    System.out.println("Session completed");
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("Invalid option, please try again");
//                    break;
//            }
//        }
//    }
//
//    private void createAccount() {
//        System.out.print("Please, enter the banking account balance (e.g., 100.55): ");
//        String balanceString = sc.next();
//        sc.nextLine();
//        if (Validator.validateInput(balanceString)) {
//            System.out.println("Please enter the account name: ");
//            String login = sc.nextLine();
//            System.out.println("Please enter the account surname: ");
//            String password = sc.nextLine();
//            System.out.println("Please enter the account name: ");
//            String name = sc.nextLine();
//            System.out.println("Please enter the account surname: ");
//            String surname = sc.nextLine();
//            System.out.println("Please choose the currency (e.g., USD, EUR): ");
//            String currency = sc.nextLine();
//            AccountDataEntity bankingAccount = new AccountDataEntity(login, password, name, surname, balanceString, currency);
//            bankingAccountService.saveAccount(bankingAccount);
//            currentAccount = bankingAccount;
//            System.out.println("Congratulations, you created a new account, please remember your ID number: ");
//            dialogue();
//        } else {
//            System.out.println("Invalid input, please try again");
//            decideOldOrNewAccount();
//        }
//    }
//
//    private void chooseExistingAccount() {
//        System.out.print("Please, enter your account ID: ");
//        int id = sc.nextInt();
//        currentAccount = bankingAccountService.findAccountById(id);
//        if (currentAccount == null) {
//            System.out.println("Account not found, please try again.");
//            decideOldOrNewAccount();
//        }
//    }
//
//    private void dialogue() {
//        while (true) {
//            System.out.println(
//                    "Choose the operation: \n" +
//                            "1 - Add to the banking account\n" +
//                            "2 - Withdraw from the banking account\n" +
//                            "3 - Transfer from the banking account\n" +
//                            "4 - See the account information\n" +
//                            "5 - Save data and exit\n"
//            );
//
//            switch (sc.nextInt()) {
//                case 1:
//                    handleDeposit();
//                    break;
//                case 2:
//                    handleWithdrawal();
//                    break;
//                case 3:
//                    handleTransfer();
//                    break;
//                case 4:
//                    System.out.println("Account Information: Login = " + currentAccount.getLogin() + ", Name = " + currentAccount.getName() + ", Balance = " + currentAccount.getBalance() + ", Currency = " + currentAccount.getCurrency());
//                    break;
//                case 5:
//                    System.out.println("Data saved and session completed");
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid option, please try again");
//                    break;
//            }
//        }
//    }
//
//    private void handleDeposit() {
//        System.out.print("Please, enter the sum to deposit (e.g., 100.55): ");
//        String amountString = sc.next();
//        if (Validator.validateInput(amountString)) {
//            operations.deposit(currentAccount, Double.parseDouble(amountString));
//            bankingAccountService.saveAccount(currentAccount);
//        } else {
//            System.out.println("Invalid amount, please try again.");
//        }
//    }
//
//    private void handleWithdrawal() {
//        System.out.print("Please, enter the sum to withdraw (e.g., 100.55): ");
//        String amountString = sc.next();
//        if (Validator.validateInput(amountString)) {
//            operations.withdraw(currentAccount, Double.parseDouble(amountString));
//            bankingAccountService.saveAccount(currentAccount);
//        } else {
//            System.out.println("Invalid amount, please try again.");
//        }
//    }
//
//    private void handleTransfer() {
//        System.out.print("Please, enter the sum to transfer (e.g., 100.55): ");
//        String amountString = sc.next();
//        if (Validator.validateInput(amountString)) {
//            double amount = Double.parseDouble(amountString);
//            System.out.print("Please, enter the target account ID: ");
//            int targetAccountId = sc.nextInt();
//
//            AccountDataEntity targetAccount = bankingAccountService.findAccountById(targetAccountId);
//
//            if (targetAccount == null) {
//                System.out.println("Target account not found, please try again.");
//                return;
//            }
//
//            operations.transfer(currentAccount, targetAccount, amount);
//            bankingAccountService.saveAccount(currentAccount);
//            bankingAccountService.saveAccount(targetAccount);
//        } else {
//            System.out.println("Invalid amount, please try again.");
//        }
//    }
//}
//
