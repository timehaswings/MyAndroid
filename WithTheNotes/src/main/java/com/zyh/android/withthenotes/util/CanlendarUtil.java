package com.zyh.android.withthenotes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhou on 2016/1/15.
 */
public class CanlendarUtil {

    public static int getYear(String dateStr) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = simpleDateFormat.parse(dateStr);
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(String dateStr) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = simpleDateFormat.parse(dateStr);
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay(String dateStr) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = simpleDateFormat.parse(dateStr);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
