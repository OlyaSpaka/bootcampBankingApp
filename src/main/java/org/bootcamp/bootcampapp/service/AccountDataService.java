package org.bootcamp.bootcampapp.service;

import jakarta.transaction.Transactional;
import org.bootcamp.bootcampapp.entity.AccountDataEntity;
import org.bootcamp.bootcampapp.repository.AccountDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;


@Service
public class AccountDataService {

    @Autowired
    AccountDataRepository accountDataRepository;

    private static final double EUR_TO_USD_RATE = 1.11;
    private static final double USD_TO_EUR_RATE = 0.9;

    DecimalFormat df = new DecimalFormat("0.00");

    @Transactional
    public AccountDataEntity saveAccountData(AccountDataDTO accountDataDTO) {
        AccountDataEntity accountDataEntity = new AccountDataEntity();

        accountDataEntity.setLogin(accountDataDTO.getLogin());
        accountDataEntity.setPassword(accountDataDTO.getPassword());
        accountDataEntity.setName(accountDataDTO.getName());
        accountDataEntity.setSurname(accountDataDTO.getSurname());
        accountDataEntity.setBalance(accountDataDTO.getBalance());
        accountDataEntity.setCurrency(accountDataDTO.getCurrency());
        accountDataRepository.save(accountDataEntity);

        return accountDataEntity;
    }

    public AccountDataDTO convertEntityToDto(AccountDataEntity accountDataEntity) {
        AccountDataDTO accountDataDTO = new AccountDataDTO();

        accountDataDTO.setId(accountDataEntity.getId());
        accountDataDTO.setLogin(accountDataEntity.getLogin());
        accountDataDTO.setPassword(accountDataEntity.getPassword());
        accountDataDTO.setName(accountDataEntity.getName());
        accountDataDTO.setSurname(accountDataEntity.getSurname());
        accountDataDTO.setBalance(accountDataEntity.getBalance());
        accountDataDTO.setCurrency(accountDataEntity.getCurrency());

        return accountDataDTO;
    }

    public Optional<AccountDataDTO> findAccountByLoginAndPassword(String login, String password) {
        Optional<AccountDataEntity> accountDataEntityOpt = accountDataRepository.findByLoginAndPassword(login, password);

        return accountDataEntityOpt.map(this::convertEntityToDto);
    }

    @Transactional
    public AccountDataDTO updateAccountData(AccountDataDTO accountDataDTO) {
        Optional<AccountDataEntity> accountDataEntityOpt = accountDataRepository.findById(accountDataDTO.getId());

        if (accountDataEntityOpt.isPresent()) {
            AccountDataEntity accountDataEntity = accountDataEntityOpt.get();
            if (!accountDataEntity.getCurrency().equals(accountDataDTO.getCurrency())) {
                double newBalance = accountDataEntity.getBalance();
                if (accountDataEntity.getCurrency().equals("EUR") && accountDataDTO.getCurrency().equals("USD")) {
                    newBalance = Double.parseDouble(df.format(newBalance *= EUR_TO_USD_RATE));
                } else if (accountDataEntity.getCurrency().equals("USD") && accountDataDTO.getCurrency().equals("EUR")) {
                    newBalance = Double.parseDouble(df.format(newBalance *= USD_TO_EUR_RATE));
                }
                accountDataEntity.setBalance(newBalance);
            }

            accountDataEntity.setLogin(accountDataDTO.getLogin());
            accountDataEntity.setPassword(accountDataDTO.getPassword());
            accountDataEntity.setName(accountDataDTO.getName());
            accountDataEntity.setSurname(accountDataDTO.getSurname());
            accountDataEntity.setCurrency(accountDataDTO.getCurrency());

            accountDataRepository.save(accountDataEntity);

            return convertEntityToDto(accountDataEntity);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public Optional<AccountDataDTO> findAccountById(int accountId) {
        Optional<AccountDataEntity> accountDataEntityOpt = accountDataRepository.findById(accountId);
        return accountDataEntityOpt.map(this::convertEntityToDto);
    }


    @Transactional
    public void deposit(int accountId, double amount) {
        Optional<AccountDataEntity> accountDataEntityOpt = accountDataRepository.findById(accountId);
        if (accountDataEntityOpt.isPresent()) {
            AccountDataEntity accountDataEntity = accountDataEntityOpt.get();
            accountDataEntity.setBalance(accountDataEntity.getBalance() + amount);
            accountDataRepository.save(accountDataEntity);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    @Transactional
    public void withdraw(int accountId, double amount) {
        Optional<AccountDataEntity> accountDataEntityOpt = accountDataRepository.findById(accountId);
        if (accountDataEntityOpt.isPresent()) {
            AccountDataEntity accountDataEntity = accountDataEntityOpt.get();
            if (accountDataEntity.getBalance() >= amount) {
                accountDataEntity.setBalance(accountDataEntity.getBalance() - amount);
                accountDataRepository.save(accountDataEntity);
            } else {
                throw new RuntimeException("Insufficient funds");
            }
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    @Transactional
    public void transferMoney(int fromAccountId, int toAccountId, double amount) {

        Optional<AccountDataEntity> fromAccountOpt = accountDataRepository.findById(fromAccountId);
        Optional<AccountDataEntity> toAccountOpt = accountDataRepository.findById(toAccountId);

        if (fromAccountOpt.isPresent() && toAccountOpt.isPresent()) {
            AccountDataEntity fromAccount = fromAccountOpt.get();
            AccountDataEntity toAccount = toAccountOpt.get();

            if (fromAccount.getBalance() >= amount) {
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);

                accountDataRepository.save(fromAccount);
                accountDataRepository.save(toAccount);

            } else {
                throw new RuntimeException("Insufficient balance for transfer");
            }
        } else {
            throw new RuntimeException("Account ID not found");
        }
    }


}





