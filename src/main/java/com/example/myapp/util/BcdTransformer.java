package com.example.myapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BcdTransformer {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_FORMATTER = "dd-MM-yyyy_HH:mm:ss";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);

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
        Calendar c = Calendar.getInstance();
        c.setTime(getDate(issueDate));
        return dateFormatter.format(c.getTime());
    }

    private static Date getDate(String issueDate) throws ParseException {
        Date date;
        date = dateFormat.parse(issueDate);
        return date;
    }

}
