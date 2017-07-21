package com.obss.Model.Services;

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Advert;
import com.obss.Model.Entities.Application;
import com.obss.Model.Entities.Extras.AdvertApplication;
import com.obss.Model.Entities.Extras.ApplicationStatus;
import com.obss.Model.Entities.Extras.SkillView;
import com.obss.Model.Entities.Skill;
import com.obss.Model.Repositories.AccountRepository;
import com.obss.Model.Repositories.AdvertRepository;
import com.obss.Model.Services.Interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnold on 7/10/2017.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AdvertRepository advertRepository;
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
    public ArrayList<AdvertApplication> getUserApplications(String email) {
        ArrayList<AdvertApplication> list = new ArrayList<>();
        Account account = accountRepository.findByEmail(email);

        for (Application app : account.getApplications()) {
            if (app.getStatus() != ApplicationStatus.DELETED) {
                AdvertApplication listObject = new AdvertApplication();
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
    public ArrayList<SkillView> getAccountSkillsForUI(Account account) {
        ArrayList<SkillView> list = new ArrayList<>();

        for (Skill skill : account.getSkills()) {
            SkillView newSkillView = new SkillView(skill.getSkillId(), skill.getSkillName());
            list.add(newSkillView);
        }
        return list;
    }

    @Override
    public int getTotalAccounts() {
        return accountRepository.countAllAccount();
    }

    @Override
    @Transactional
    public void applyToAdvert(int adCode, String email) {
        Account account = accountRepository.findByEmail(email);
        Advert advert = advertRepository.findOne(adCode);
        account.applyToAdvert(advert);
        accountRepository.save(account);
    }


}
