package com.kabanov.scheduler.actions_table;

import org.junit.Assert;
import org.junit.Test;

import com.kabanov.scheduler.test_utils.ActionTestUtils;
import com.kabanov.scheduler.test_utils.TestUtils;
import com.kabanov.scheduler.utils.TimeUtils;

/**
 * @author Алексей
 * @date 21.05.2019
 */
public class ActionDataTest {

    @Test
    public void getNextExecutionDate() {
        ActionData actionData = ActionTestUtils.createAction("first", 1, TestUtils.toDate("13.05.2008"));
        Assert.assertEquals(
                TimeUtils.cutWithDayAcc(TestUtils.toDate("14.05.2008")),
                TimeUtils.cutWithDayAcc(actionData.getNextExecutionDate()));

        actionData.setPeriodicityDays(2);
        Assert.assertEquals(
                TimeUtils.cutWithDayAcc(TestUtils.toDate("15.05.2008")),
                TimeUtils.cutWithDayAcc(actionData.getNextExecutionDate()));
        
        actionData.setExecutedAt(TestUtils.toDate("20.08.2012"));
        Assert.assertEquals(
                TimeUtils.cutWithDayAcc(TestUtils.toDate("22.08.2012")),
                TimeUtils.cutWithDayAcc(actionData.getNextExecutionDate()));
    }
}