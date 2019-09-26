package com.example.myapp.util;

import java.text.SimpleDateFormat;

public interface Constants {
    String DATE_FORMAT = "dd-MM-yyyy";
    String DATE_FORMATTER = "dd-MM-yyyy_HH:mm:ss";
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);
}
