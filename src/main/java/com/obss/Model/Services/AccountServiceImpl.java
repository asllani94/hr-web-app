package com.obss.Model.Services;

import com.obss.Model.Entities.Account;
import com.obss.Model.Repositories.AccountRepository;
import com.obss.Model.Services.Interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arnold on 7/10/2017.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void createOrUpdateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account loadAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account loadAccountByAccountId(int accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    @Override
    public List<Account> loadAllAccounts() {
        return accountRepository.findAll();
    }
}
