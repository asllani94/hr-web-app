package com.obss;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arnold on 7/22/2017.
 */
public class TimeConversionTest {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("22/07/2017");
        Timestamp ts = new Timestamp(date.getTime());

        String S = dateFormat.format(ts);

        System.out.println(ts);
        System.out.println(S);
    }
}
