package com.enav.cazaunchollo.cazaunchollo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormat {

    // Método para devolver la fecha en el formato dd/M/yyyy HH:mm
    public String devolverFecha() {

        Date strToDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy HH:mm");
        String DateToStr = format.format(strToDate);

    return DateToStr;
    }
}
