package com.kabanov.scheduler.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss.SSS", Locale.getDefault());
    private static SimpleDateFormat userDateFormat = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());

    public static long cutWithDayAcc(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    public static void sleepForMillisecond() {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() == start) {
            // do nothing
        }
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
    
    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static long getTime10AMGivenDay(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        return calendar.getTimeInMillis();
    }

    public static boolean isAfter10AM(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        return calendar.getTimeInMillis() < time.getTime();
    }

    public static String toReadableTime(long time) {
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    public static String toUserTime(long time) {
        Date date = new Date(time);
        return userDateFormat.format(date);
    }
}
