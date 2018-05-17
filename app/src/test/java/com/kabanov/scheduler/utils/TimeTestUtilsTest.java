package com.kabanov.scheduler.utils;

import com.google.common.util.concurrent.Uninterruptibles;

import junit.framework.Assert;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeTestUtilsTest {

    @Test
    public void cutWithDayAcc() {
        Date first = new Date();
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MILLISECONDS);

        Assert.assertEquals(TimeUtils.cutWithDayAcc(first), TimeUtils.cutWithDayAcc(new Date()));
    }

    @Test
    public void addDaysTest() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        Date date = format.parse("01.05.01");

        Date result = TimeUtils.addDays(date, 2);

        String newDate = format.format(result);

        Assert.assertEquals("03.05.01", newDate);
    }
}