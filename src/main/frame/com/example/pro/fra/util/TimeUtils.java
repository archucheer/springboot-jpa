package com.example.pro.fra.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtils {

    private static final String SIMPLE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String getSimpleDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(SIMPLE_DATE_TIME));
    }

    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static LocalDateTime convertStrToLDT(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static Date convertStrToDate(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_TIME);
        return sdf.parse(dateTimeStr);
    }

    public static Date convertStrToDate(String dateTimeStr, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateTimeStr);
    }

    public static void main(String[] args) {
        try {
            System.out.println(convertStrToDate("1994-03-17","yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(LocalDate.now());
//
//        System.out.println(LocalTime.now());
//
//        System.out.println(getSimpleDateTime());
//
//        System.out.println(convertStrToLDT("2018-05-02 22:51:27", SIMPLE_DATE_TIME).toLocalTime());
//
//        System.out.println(System.currentTimeMillis());
    }
}
