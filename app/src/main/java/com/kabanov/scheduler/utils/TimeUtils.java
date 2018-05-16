package com.kabanov.scheduler.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static long cutWithDayAcc(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
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
}
