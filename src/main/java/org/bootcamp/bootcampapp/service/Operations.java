package org.bootcamp.bootcampapp.service;

import org.bootcamp.bootcampapp.entity.AccountDataEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class Operations {

    private final DecimalFormat df = new DecimalFormat("#.##");

    public void deposit(AccountDataEntity account, double amount) {
        if (amount < 0) {
            System.out.println("Deposit amount must be positive.");
        } else {
            account.setBalance(account.getBalance() + amount);
            System.out.println("Deposited: " + df.format(amount));
        }
    }

    public void withdraw(AccountDataEntity account, double amount) {
        if (amount < 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > account.getBalance()) {
            System.out.println("Insufficient balance to withdraw!");
        } else {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Withdrew: " + df.format(amount));
        }
    }

    public void transfer(AccountDataEntity fromAccount, AccountDataEntity toAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than 0.");
        } else if (amount > fromAccount.getBalance()) {
            System.out.println("Insufficient balance to transfer!");
        } else {
            withdraw(fromAccount, amount);
            deposit(toAccount, amount);
            System.out.println("Transferred: " + df.format(amount) + " to account: " + toAccount.getName() + " " + fromAccount.getSurname());
        }
    }
}
