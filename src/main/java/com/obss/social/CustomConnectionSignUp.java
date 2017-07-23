package com.obss.social;

/**
 * Created by arnold on 7/10/2017.
 * This class is used to implicity register user if its his first time using web app
 */

import com.obss.Model.Entities.Advert;
import com.obss.Model.Entities.Skill;
import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.AccountDetails;
import com.obss.Model.Services.AdvertServiceImpl;
import com.obss.Utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.UUID;

@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

    private final AccountServiceImpl usersDao;

    private final AdvertServiceImpl advertService;



    @Autowired
    public CustomConnectionSignUp(AccountServiceImpl usersDao, AdvertServiceImpl advertService) {
        this.usersDao = usersDao;
        this.advertService = advertService;
    }


    public String execute(Connection<?> connection) {
       //LinkedIn template= (LinkedIn) connection.getApi();
        System.out.println("ConnectionSignUp called");
        UserProfile profile = connection.fetchUserProfile();
        String imagerUrl = connection.getImageUrl();

        /*  LinkedInProfileFull full=template.profileOperations().getUserProfileFull();
        List<Company> companies=template.companyOperations().getFollowing();*/

        //Unique linkedin user id
        String email=profile.getEmail();
        Account user=new Account(email,profile.getFirstName(),profile.getLastName(),generateUserPassword());
        user.setImageUrl(imagerUrl);

        //here we get extra info from LinkedIn API (using mock because not approved)
        AccountDetails accountDetails=new AccountDetails();
        accountDetails.setTitle("Software Developer");
        accountDetails.setAddress("Rasimpasa Mah, Kadikoy-Istanbul ");
        accountDetails.setEducation("Istanbul Teknik Universitesi");
        accountDetails.setExperience("3 years of experience");
        user.setAccountDetails(accountDetails);

        Skill skill = new Skill();
        skill.setSkillName("Yazilim Mimari");

        Skill skill_1 = new Skill();
        skill_1.setSkillName("Spring EE");

        user.addSkill(skill);
        user.addSkill(skill_1);

        populateDB(user);
        return email;
    }
    private String generateUserPassword(){
        return UUID.randomUUID().toString();

    }

    //only for testing
    private void populateDB(Account account) {
        Advert advert = new Advert();
        advert.setAdHeader("Garson araniyor!!");

        advert.setAdDescription("ADAMAR A.Ş. Şubelerinde görevlendirilmek üzere\n" +
                "GARSON & BARMEN & BARİSTA takım arkadaşları arıyoruz.");
        advert.setAdStatus(false);
        advert.setAdActivationTime(new Timestamp(5000));
        advert.setAdDeadlineTime(new Timestamp(5000));
        advert.setAdJobTitle("Backend developer");
        advert.setAdJobLocation("Pendik, Istanbul");
        advert.setAdQualifications(" MYO mezunu yada en az Lise mezunu \n" +
                "• En az 1 sene servis tecrübesine sahip\n" +
                "• Vardiyalı sistemde çalışabilecek \n" +
                "• Bay veya bayan \n" +
                "• Takım çalışmasına yatkın \n" +
                "• Güler yüzlü \n" +
                "• Dış görünüşüne özen gösteren \n" +
                "• İşini seven, hızlı, dinamik ve titiz \n" +
                "• Esnek çalışma saatlerine ayak uydurabilecek \n" +
                "• Müşteri memnuniyetini ilke edinmiş \n" +
                "• Sektörde gelişmeyi ve kariyer yapmayı hedefleyen pozitif calışma arkadaşları arıyoruz. \n" +
                "(Personel Lojmanımız Yoktur)");


        Advert advert1 = new Advert();
        advert1.setAdHeader("C++ developer araniyor");

        advert1.setAdDescription("This is a test advert created at run time");
        advert1.setAdStatus(false);

        try {
            advert1.setAdActivationTime(DateUtil.getCurrentTimestamp());
            advert1.setAdDeadlineTime(DateUtil.getTimestampFromDate("07/28/2017"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        advertService.createAdvert(advert);
        advertService.createAdvert(advert1);

        try {
            account.applyToAdvert(advert);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        usersDao.createOrUpdateAccount(account);

    }

}