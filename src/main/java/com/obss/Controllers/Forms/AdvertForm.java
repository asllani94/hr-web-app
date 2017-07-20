package com.obss.Controllers.Forms;


import com.obss.Model.Entities.Advert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Created by arnold on 7/20/2017.
 */
public class AdvertForm {

    private int adCode;

    @NotNull
    @Size(min = 1, max = 150)
    private String adHead;
    @NotNull
    @Size(min = 1, max = 150)
    private String adTitle;
    @NotNull
    private String adAddress;
    @NotNull
    private String adDescription;

    private String adQualification;


    private boolean update;


    private String adActivation;
    @NotNull
    private String adDeadline;

    public String getAdHead() {
        return adHead;
    }

    public void setAdHead(String adHeader) {
        this.adHead = adHeader;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdAddress() {
        return adAddress;
    }

    public void setAdAddress(String adAddress) {
        this.adAddress = adAddress;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public String getAdQualification() {
        return adQualification;
    }

    public void setAdQualification(String adQualification) {
        this.adQualification = adQualification;
    }

    public String getAdActivation() {
        return adActivation;
    }

    public void setAdActivation(String adActivation) {
        this.adActivation = adActivation;
    }

    public String getAdDeadline() {
        return adDeadline;
    }

    public void setAdDeadline(String adDeadline) {
        this.adDeadline = adDeadline;
    }

    public Advert buildAdvert() {

        Advert advert = new Advert();
        advert.setAdHeader(this.adHead);
        advert.setAdJobTitle(this.adTitle);
        advert.setAdJobLocation(this.adAddress);
        advert.setAdDescription(this.adDescription);
        advert.setAdQualifications(this.adQualification);
        advert.setAdActivationTime(new Timestamp(885));
        advert.setAdDeadlineTime(new Timestamp(555));
        return advert;
    }


    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getAdCode() {
        return adCode;
    }

    public void setAdCode(int adCode) {
        this.adCode = adCode;
    }
}
