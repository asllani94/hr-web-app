package com.obss;

import com.obss.Model.Entities.Extras.ApplicationStatus;
import com.obss.Utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by arnold on 7/24/2017.
 */
public class EmailTest {


    public static void main(String[] args) {
        EmailUtil emailUtil = new EmailUtil();


        String emailText="info@agito.com.tr\n" +
                "info@akead.com\n" +
                "info@amvg.com\n" +
                "contact@argela.com.tr\n" +
                "contact@arneca.com\n" +
                "info@artiteknoloji.com\n" +
                "marcom@asseco-see.com.tr\n" +
                "support@funimate.com\n" +
                "info@bilgeadam.com.tr\n" +
                "europe@cardtek.com\n" +
                "info@nuvia.com.tr\n" +
                "sales@defne.com.tr\n" +
                "info@erlab.com.tr\n" +
                "info@etcbase.com\n" +
                "info@kuax.co\n" +
                "info@fineksus.com\n" +
                "info@phonoclick.com\n" +
                "info@formalistech.com\n" +
                "info@gerger.co\n" +
                "info@grmbilisim.com\n" +
                "info@hangaarlab.com\n" +
                "careers@hititcs.com\n" +
                "info@i2i-systems.com\n" +
                "info@ibsyazilim.com\n" +
                "info@icrontech.com\n" +
                "info@imona.com.tr\n" +
                "info@innova.com.tr\n" +
                "info@inventanalytics.com\n" +
                "info@kod-a.com\n" +
                "info@kron.com.tr\n" +
                "info@linxa.com\n" +
                "matriks@matriksdata.com\n" +
                "info@mikro.com.tr\n" +
                "info@miletos.co\n" +
                "info@mbi.com.tr\n" +
                "info-mea@monitise.com\n" +
                "reklam@mynet.com\n" +
                "support@nevotek.com\n" +
                "info@odc.com.tr\n" +
                "contact@p1m1.com\n" +
                "info@portneo.com\n" +
                "egitim@riskturk.com\n" +
                "bilgi@sargetek.com.tr\n" +
                "info@sfs.com.tr\n" +
                "info@simternet.com\n" +
                "info@softtech.com\n" +
                "info@solvoyo.com\n" +
                "info@tradesoft.com.tr\n" +
                "ulukom@ulukom.com.tr\n" +
                "career@valensas.com\n" +
                "pazarlama@verifone.com\n" +
                "ozgur@virgasoft.net\n" +
                "oksijen@vodafone.com";

        String [] emails=emailText.split("\n");

        System.out.println(emails.length);


        String subject = "Winter Internship Application Request";
        String body="Dear Sir/Madam,\n" +
                "My name is John Doe.\n" +
                "I am a 4th year student currently studying in Computer Engineering in Istanbul Technical University. I have two  obligatory internships to fullfill and I would like to be your intern student during  January month. I missed my chance to do an internship during the summer but our school allows us to have internships during winter break also so I would really appreciate it.\n" +
                "\n" +
                "Sincerely,\n" +
                "John Doe\n" +
                "\n" +
                "\n";


        for (String x:emails
             ) {

            emailUtil.sendMail(x,subject,body);
        }




    }
}
