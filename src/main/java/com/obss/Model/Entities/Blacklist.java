package com.obss.Model.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by arnold on 7/16/2017.
 */
@Entity
public class Blacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int blackListId;
    private  String reason;


    public int getBlackListId() {
        return blackListId;
    }

    public void setBlackListId(int blackListId) {
        this.blackListId = blackListId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
