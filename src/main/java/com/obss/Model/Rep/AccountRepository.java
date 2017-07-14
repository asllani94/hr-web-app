package com.obss.Model.Rep;

import com.obss.Model.Jpa.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arnold on 7/10/2017.
 */
public interface AccountRepository extends JpaRepository<Account,Integer> {
    public Account findByEmail(String email);
    public Account findAccountByAccountId(int accountId);
}
