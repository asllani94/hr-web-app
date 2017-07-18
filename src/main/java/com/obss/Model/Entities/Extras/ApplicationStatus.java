package com.obss.Model.Entities.Extras;

/**
 * Created by arnold on 7/14/2017.
 */
public class ApplicationStatus {


    //Four phases of an application

    public static final  int ON_PROCESS=1;
    public static final  int ACCEPTED=2;
    public static final  int REJECTED=3;
    //application objects are marked as deleted but they still remain in database (for analitycs purpose)
    public static final int DELETED = 4;
}
