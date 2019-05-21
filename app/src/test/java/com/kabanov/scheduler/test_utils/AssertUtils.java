package com.kabanov.scheduler.test_utils;

import java.util.Date;

import com.kabanov.scheduler.utils.TimeUtils;

import junit.framework.Assert;

/**
 * @author Алексей
 * @date 21.05.2019
 */
public class AssertUtils {
    
    public static void assertEqualsDate(Date expected, Date actual) {
        Assert.assertEquals(
                TimeUtils.cutWithDayAcc(expected),
                TimeUtils.cutWithDayAcc(actual)
        );
    }
}
