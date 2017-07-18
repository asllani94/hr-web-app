package com.obss.Model.Services;

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Application;
import com.obss.Model.Entities.Extras.ApplicationDetails;
import com.obss.Model.Entities.Extras.ApplicationStatus;
import com.obss.Model.Entities.Extras.UiSkill;
import com.obss.Model.Entities.Skill;
import com.obss.Model.Repositories.AccountRepository;
import com.obss.Model.Services.Interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    @Override
    public ArrayList<ApplicationDetails> getUserApplications(String email) {
        ArrayList<ApplicationDetails> list = new ArrayList<>();
        Account account = accountRepository.findByEmail(email);

        for (Application app : account.getApplications()) {
            if (app.getStatus() != ApplicationStatus.DELETED) {
                ApplicationDetails listObject = new ApplicationDetails();
                listObject.setAdCode(app.getAdvert().getAdCode());
                listObject.setAdHeader(app.getAdvert().getAdHeader());
                listObject.setStatus(app.getStatus());
                //timestamp must be added

                list.add(listObject);

            }


        }
        return list;
    }

    @Override
    public ArrayList<UiSkill> getAccountSkillsForUI(Account account) {
        ArrayList<UiSkill> list = new ArrayList<>();
        for (Skill skill : account.getSkills()) {
            UiSkill newUiSkill = new UiSkill(skill.getSkillId(), skill.getSkillName());
            list.add(newUiSkill);
        }
        return list;
    }
}
