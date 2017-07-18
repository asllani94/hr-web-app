package com.obss.Model.Services.Interfaces;

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Extras.ApplicationDetails;
import com.obss.Model.Entities.Extras.UiSkill;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnold on 7/17/2017.
 */
@Service
public interface AccountService {

    public void createOrUpdateAccount(Account account);

    public Account loadAccountByEmail(String email);

    public Account loadAccountByAccountId(int accountId);

    public List<Account> loadAllAccounts();

    public ArrayList<ApplicationDetails> getUserApplications(String email);

    public List<UiSkill> getAccountSkillsForUI(Account account);

}
