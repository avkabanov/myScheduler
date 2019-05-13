package com.kabanov.scheduler.utils;

import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.add_action.NewAction;

import junit.framework.Assert;

/**
 * @author Алексей
 * @date 13.05.2019
 */
public class AssertUtils {
    public static void assertEquals(NewAction expected, ActionData actual) {
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getPeriodicityDays().intValue(), actual.getPeriodicityDays());
        Assert.assertEquals(expected.getLastExecutedDate(), actual.getLastExecutionDate());
        
        
    }
}
