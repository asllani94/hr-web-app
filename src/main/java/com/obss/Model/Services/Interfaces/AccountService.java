package com.obss.Model.Services.Interfaces;

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Advert;
import com.obss.Model.Entities.Extras.AdvertApplication;
import com.obss.Model.Entities.Extras.SkillView;
import org.springframework.stereotype.Service;

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

    public ArrayList<AdvertApplication> getUserApplications(String email);

    public List<SkillView> getAccountSkillsForUI(Account account);

    public int getTotalAccounts();

    public void applyToAdvert(int adCode, String email);


}
