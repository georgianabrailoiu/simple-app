package com.example.myapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BcdTransformer {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_FORMATTER = "dd-MM-yyyy_HH:mm:ss";

    public static String getBcdMM(String issueDate) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(getDate(issueDate));
        String bcdMM = Integer.toString(c.get(Calendar.MONTH) + 1);

        if (Integer.parseInt(bcdMM) < 10) {
            bcdMM = "0" + bcdMM;
        }
        return bcdMM;
    }

    public static String getBcdFull(String issueDate) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);
        Calendar c = Calendar.getInstance();
        c.setTime(getDate(issueDate));
        return dateFormatter.format(c.getTime());
    }

    private static Date getDate(String issueDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date;
        date = dateFormat.parse(issueDate);
        return date;
    }
}
