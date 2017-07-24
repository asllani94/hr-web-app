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
        emailUtil.notifyStatusChange("asllani16@itu.edu.tr", ApplicationStatus.REJECTED, 911);

    }
}
