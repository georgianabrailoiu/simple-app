package com.example.myapp.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class BcdTransformer implements Constants {

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
        Date date = null;
        date = dateFormat.parse(issueDate);
        return date;
    }

}
