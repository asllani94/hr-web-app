package com.obss.Model.Repositories;

import com.obss.Model.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by arnold on 7/10/2017.
 */
public interface AccountRepository extends JpaRepository<Account,Integer> {
    public Account findByEmail(String email);
    public Account findByAccountId(int accountId);
    public void  deleteByAccountId(int accountId);
    public  void  deleteByEmail(String email);

    @Query("select count(a) from Account a")
    int countAllAccount();
}
