package com.enav.cazaunchollo.cazaunchollo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Edgar on 07/05/2017.
 */

public class DateFormat {

    public String devolverFecha() {

        Date strToDate = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy HH:mm");
        String DateToStr = format.format(strToDate);

        try {
            strToDate = format.parse(DateToStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    return DateToStr;
    }
}
