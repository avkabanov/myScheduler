package com.kabanov.scheduler.utils;

import com.google.common.util.concurrent.Uninterruptibles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.junit.Test;

public class TimeUtilsTest {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void cutWithDayAcc() {
        Date date = new Date();
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MILLISECONDS);
        Assert.assertEquals(TimeUtils.cutWithDayAcc(date), TimeUtils.cutWithDayAcc(new Date()));
        
        
        {
            Calendar first = Calendar.getInstance();
            first.setTime(new Date());
            first.set(Calendar.HOUR_OF_DAY, 0);
            first.set(Calendar.MINUTE, 0);
            first.set(Calendar.SECOND, 0);
            first.set(Calendar.MILLISECOND, 1);

            Calendar second = Calendar.getInstance();
            second.setTime(new Date());
            second.set(Calendar.HOUR_OF_DAY, 23);
            second.set(Calendar.MINUTE, 59);
            second.set(Calendar.SECOND, 59);
            second.set(Calendar.MILLISECOND, 999);

            Assert.assertEquals(TimeUtils.cutWithDayAcc(first.getTime()), TimeUtils.cutWithDayAcc(second.getTime()));
        }
    }

    @Test
    public void addDaysTest() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        {
            Date date = format.parse("01.05.01");
            Date result = TimeUtils.addDays(date, 2);
            String newDate = format.format(result);
            Assert.assertEquals("03.05.01", newDate);
        }
        {
            Date date = format.parse("31.12.17");
            Date result = TimeUtils.addDays(date, 2);
            String newDate = format.format(result);
            Assert.assertEquals("02.01.18", newDate);
        }
    }

    @Test
    public void isAfter10AmTest() {
        Calendar before = Calendar.getInstance();
        before.setTime(new Date());
        before.set(Calendar.HOUR, 9);
        before.set(Calendar.MINUTE, 59);
        before.set(Calendar.SECOND, 59);
        before.set(Calendar.MILLISECOND, 999);
        before.set(Calendar.AM_PM, Calendar.AM);
        
        Assert.assertFalse(TimeUtils.isAfter10AM(before.getTime()));

        Calendar after = Calendar.getInstance();
        after.setTime(new Date());
        after.set(Calendar.HOUR, 10);
        after.set(Calendar.MINUTE, 0);
        after.set(Calendar.SECOND, 0);
        after.set(Calendar.MILLISECOND, 1);
        after.set(Calendar.AM_PM, Calendar.AM);

        Assert.assertTrue(TimeUtils.isAfter10AM(after.getTime()));
    }

    @Test
    public void shouldPrintDataInUserDateFormat() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        Date date = format.parse("01/05/88");

        String time = TimeUtils.toUserTime(date.getTime());
        
        Assert.assertEquals("Sun, 01 May 1988", time);
    }
    
    public static Date toDate(String date) {
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(date);
        }
    }
}