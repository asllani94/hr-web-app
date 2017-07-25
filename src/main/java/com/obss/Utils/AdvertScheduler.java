package com.obss.Utils;

import com.obss.Model.Entities.Advert;
import com.obss.Model.Entities.Extras.AdvertStatus;
import com.obss.Model.Services.AdvertServiceImpl;
import com.obss.Utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

/**
 * Created by arnold on 7/24/2017.
 * A task scheduler that runs every 24 hours to checks whether
 * an advert post needs to be activated or deactivated
 */

@Configuration
@EnableScheduling
public class AdvertScheduler {

    @Autowired
    private AdvertServiceImpl advertService;

    //run every 24 hours
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void activateAndDeactivateAdverts() {
        Timestamp todayDate = null;
        try {
            todayDate = DateUtil.getCurrentTimestamp();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Advert> adverts = advertService.loadAllAdverts();
        for (Advert ad : adverts) {
            if (ad.getAdActivationTime() == todayDate)
                ad.setAdStatus(AdvertStatus.ACTIVE_ADVERT);
            if (ad.getAdDeadlineTime() == todayDate)
                ad.setAdStatus(AdvertStatus.DEACTIVE_ADVERT);

            advertService.createAdvert(ad);
        }

        System.out.println("Advert Scheduler is executed!");
    }
}
