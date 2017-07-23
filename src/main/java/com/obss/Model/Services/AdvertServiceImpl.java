package com.obss.Model.Services;

import com.obss.Controllers.Forms.AdvertForm;
import com.obss.Model.Entities.Advert;
import com.obss.Model.Entities.Application;
import com.obss.Model.Entities.Extras.ApplicationStatus;
import com.obss.Model.Entities.Extras.SkillView;
import com.obss.Model.Entities.Extras.UserApplication;
import com.obss.Model.Entities.Extras.UserApplicationComparator;
import com.obss.Model.Entities.Skill;
import com.obss.Model.Repositories.AdvertRepository;
import com.obss.Model.Services.Interfaces.AdvertService;
import com.obss.Utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by arnold on 7/17/2017.
 * Implementation of AdvertService Interface
 */
@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertRepository advertRepository;

    @Override
    public List<Advert> loadAllActiveAdverts() {
        return advertRepository.findByAdStatus(false);
    }

    @Override
    public List<Advert> loadAllAdverts() {
        return advertRepository.findAll();
    }


    @Override
    public Advert loadAdvertByAdCode(int adCode) {
        return advertRepository.findOne(adCode);
    }

    @Override
    public void createAdvert(Advert advert) {
        advertRepository.saveAndFlush(advert);
    }

    @Override
    public void deleteAdvert(int adCode) {
        Advert advert = advertRepository.findOne(adCode);
        advertRepository.delete(advert);
    }

    @Override
    public List<SkillView> getAdvertSkillsForUI(Advert advert) {
        ArrayList<SkillView> list = new ArrayList<>();
        for (Skill skill : advert.getSkills()) {
            SkillView newSkillView = new SkillView(skill.getSkillId(), skill.getSkillName());
            list.add(newSkillView);
        }
        return list;
    }

    @Override
    public int getTotalAdverts() {
        return advertRepository.countAllAdvert();
    }

    @Override
    public void updateAdvert(AdvertForm advertForm) {
        Advert advert = advertRepository.findOne(advertForm.getAdCode());
        advert.setAdHeader(advertForm.getAdHead());
        advert.setAdJobTitle(advertForm.getAdTitle());
        advert.setAdJobLocation(advertForm.getAdAddress());
        advert.setAdDescription(advertForm.getAdDescription());
        advert.setAdQualifications(advertForm.getAdQualification());
        try {
            advert.setAdActivationTime(DateUtil.getTimestampFromDate(advertForm.getAdActivation()));
            advert.setAdDeadlineTime(DateUtil.getTimestampFromDate(advertForm.getAdDeadline()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        advert.setSkills(advertForm.getSkillList());
        advertRepository.save(advert);

    }

    @Override
    public List<UserApplication> getCandidateApplications(int adCode) {
        Advert advert = advertRepository.findOne(adCode);

        ArrayList<UserApplication> list = new ArrayList<>();

        for (Application app : advert.getApplications()) {
            if (app.getStatus() != ApplicationStatus.DELETED) {
                UserApplication newUserApp = new UserApplication();
                newUserApp.setAccountId(app.getAccount().getAccountId());
                newUserApp.setFullName(app.getAccount().getFirstName() + " " + app.getAccount().getLastName());
                newUserApp.setImageUrl(app.getAccount().getImageUrl());
                newUserApp.setSkills(app.getAccount().getSkills());
                newUserApp.setStatus(app.getStatus());
                newUserApp.setFilterTag(getFilterTagByStatus(app.getStatus()));
                newUserApp.setAdCode(app.getAdvert().getAdCode());
                list.add(newUserApp);
            }
        }

        Set<Skill> requiredSkills = advert.getSkills();
        list.sort(new UserApplicationComparator(requiredSkills));
        return list;
    }

    @Override
    public void activateAdvert(int adCode) {
        Advert advert = advertRepository.findOne(adCode);
        advert.setAdStatus(false);
        advertRepository.save(advert);
    }

    @Override
    public void deactivateAdvert(int adCode) {
        Advert advert = advertRepository.findOne(adCode);
        advert.setAdStatus(true);
        advertRepository.save(advert);
    }

    private String getFilterTagByStatus(int status) {
        switch (status) {
            case ApplicationStatus.ON_PROCESS:
                return "bekleyen";
            case ApplicationStatus.ACCEPTED:
                return "onaylanan";
            case ApplicationStatus.REJECTED:
                return "iptal";
            default:
                return "none";
        }

    }
}
