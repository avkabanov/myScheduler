package com.kabanov.scheduler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.util.concurrent.Uninterruptibles;

import junit.framework.Assert;

public class TimeUtilsTest {

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
            second.set(Calendar.MILLISECOND, 100);

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
}