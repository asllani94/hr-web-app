package com.obss.Model.Dao;

import com.obss.Model.Jpa.Account;
import com.obss.Model.Rep.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by arnold on 7/10/2017.
 */
@Service
public class AccountDao {

    @Autowired
    AccountRepository accountRepository;
    public void createUser(Account user)
    {
        accountRepository.save(user);

    }


    //Id or username? need to be checked
    public Account loadUserByEmail(String email) {
        return  accountRepository.findByEmail(email);

    }
}
