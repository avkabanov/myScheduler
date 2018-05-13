package com.kabanov.scheduler.utils;

import com.google.common.util.concurrent.Uninterruptibles;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtilsTest {

    @Test
    public void cutWithDayAcc() {
        Date first = new Date();
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MILLISECONDS);

        Assert.assertEquals(TimeUtils.cutWithDayAcc(first), TimeUtils.cutWithDayAcc(new Date()));
    }
}