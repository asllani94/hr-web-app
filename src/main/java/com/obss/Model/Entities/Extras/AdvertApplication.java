package com.obss.Model.Entities.Extras;

import java.security.Timestamp;

/**
 * Created by arnold on 7/18/2017.
 * This class is not an entity. Its only a class used to populate application list in the view.
 */
public class AdvertApplication {
    private int adCode;
    private String adHeader;
    private Timestamp applicationTime;
    private int status;


    public int getAdCode() {
        return adCode;
    }

    public void setAdCode(int adCode) {
        this.adCode = adCode;
    }

    public String getAdHeader() {
        return adHeader;
    }

    public void setAdHeader(String adHeader) {
        this.adHeader = adHeader;
    }

    public Timestamp getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Timestamp applicationTime) {
        this.applicationTime = applicationTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
