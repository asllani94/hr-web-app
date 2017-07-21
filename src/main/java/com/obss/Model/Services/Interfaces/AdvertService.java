package com.obss.Model.Services.Interfaces;

import com.obss.Controllers.Forms.AdvertForm;
import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Advert;
import com.obss.Model.Entities.Extras.SkillView;
import com.obss.Model.Entities.Extras.UserApplication;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arnold on 7/17/2017.
 */
@Service
public interface AdvertService {

    public List<Advert> loadAllActiveAdverts();

    public List<Advert> loadAllAdverts();

    public Advert loadAdvertByAdCode(int adCode);

    public void createAdvert(Advert advert);

    public void deleteAdvert(int adCode);

    public List<SkillView> getAdvertSkillsForUI(Advert advert);

    public int getTotalAdverts();

    public void updateAdvert(AdvertForm advertForm);

    public List<UserApplication> getCandidateApplications(int adCode);


}
