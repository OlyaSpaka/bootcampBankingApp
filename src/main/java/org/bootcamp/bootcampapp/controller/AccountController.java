package org.bootcamp.bootcampapp.controller;

import org.bootcamp.bootcampapp.service.AccountDataDTO;
import org.bootcamp.bootcampapp.service.AccountDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountDataService accountDataService;

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/account")
    public String showForm(Model model) {
        model.addAttribute("accountData", new AccountDataDTO());
        return "form";
    }

    @PostMapping("/account")
    public String submitForm(@ModelAttribute AccountDataDTO accountData, Model model) {
        model.addAttribute("accountData", accountData);
        accountDataService.saveAccountData(accountData);
        return "result";
    }

    @GetMapping("/search")
    public String getSearchForm(Model model) {
        model.addAttribute("accountData", new AccountDataDTO());
        return "search";
    }

    @PostMapping("/search-result")
    public String searchResult(@RequestParam String login, @RequestParam String password, Model model) {
        Optional<AccountDataDTO> accountDataOpt = accountDataService.findAccountByLoginAndPassword(login, password);

        if (accountDataOpt.isPresent()) {
            model.addAttribute("accountData", accountDataOpt.get());
            return "searchResult";
        } else {
            model.addAttribute("message", "Account not found");
            return "search";
        }
    }


    @PostMapping("/update-account")
    public String updateAccount(@ModelAttribute AccountDataDTO accountData, Model model) {
        var updatesData = accountDataService.updateAccountData(accountData);
        model.addAttribute("accountData", updatesData);
        return "result";
    }

    @GetMapping("/operations")
    public String showOperationsForm(@RequestParam int accountId, Model model) {
        Optional<AccountDataDTO> accountDataOpt = accountDataService.findAccountById(accountId);

        if (accountDataOpt.isPresent()) {
            model.addAttribute("accountData", accountDataOpt.get());
            return "operations";
        } else {
            model.addAttribute("message", "Account not found");
            return "search";
        }
    }

    @PostMapping("/perform-operation")
    public String performOperation(@RequestParam int accountId,
                                   @RequestParam String operation,
                                   @RequestParam double amount,
                                   Model model) {
        String message = null;
        String errorMessage = null;

        try {
            if ("deposit".equals(operation)) {
                accountDataService.deposit(accountId, amount);
                message = "Deposit successful!";
            } else if ("withdraw".equals(operation)) {
                accountDataService.withdraw(accountId, amount);
                message = "Withdrawal successful!";
            }

            Optional<AccountDataDTO> updatedAccountDataOpt = accountDataService.findAccountById(accountId);
            if (updatedAccountDataOpt.isPresent()) {
                model.addAttribute("accountData", updatedAccountDataOpt.get());
            } else {
                errorMessage = "Account not found";
            }
        } catch (Exception e) {
            errorMessage = "Error occurred: " + e.getMessage();
        }

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "operations";
    }


    @PostMapping("/perform-transfer")
    public String performTransfer(@RequestParam("fromAccountId") int fromAccountId,
                                  @RequestParam("toAccountId") int toAccountId,
                                  @RequestParam("transferAmount") double transferAmount,
                                  Model model) {
        String message = null;
        String errorMessage = null;

        try {
            accountDataService.transferMoney(fromAccountId, toAccountId, transferAmount);
            message = "Transfer successful!";
        } catch (Exception e) {
            errorMessage = "Transfer failed: " + e.getMessage();
        }

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("accountData", accountDataService.findAccountById(fromAccountId).orElse(null));

        return "operations";
    }


}
