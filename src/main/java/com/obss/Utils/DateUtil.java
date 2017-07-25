package com.obss.Utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arnold on 7/22/2017.
 * A helper class that manages conversion between a string date and a TimeStamp object.
 * Throws ParseExeption if provided string isn't convertible.
 */
public class DateUtil {

    static final String DATE_FORMAT = "MM/dd/yyyy";

    public static String getDateFromTimestamp(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(timestamp);
    }

    public static Timestamp getTimestampFromDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date retrievedDate = dateFormat.parse(date);
        return new Timestamp(retrievedDate.getTime());
    }

    public static Timestamp getCurrentTimestamp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date now = new Date();
        String strDate = dateFormat.format(now);
        return getTimestampFromDate(strDate);
    }


    public static boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date parsed = dateFormat.parse(date);

            if (parsed.getTime() < (new Date().getTime()))
                return false;

        } catch (ParseException e) {
            return false;
        }

        return true;
    }

}
