package com.kabanov.scheduler.test_utils;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.kabanov.scheduler.actions_table.ActionData;

/**
 * @author Алексей
 * @date 21.05.2019
 */
public class ActionTestUtils {
    @Nonnull
    public static ActionData createAction(String name, int periodicity, Date lastExecuted) {
        ActionData actionData = new ActionData(UUID.randomUUID().toString(), name, periodicity);
        actionData.setExecutedAt(lastExecuted);
        return actionData;
    }
}
