package com.kabanov.scheduler.test_utils;

import com.kabanov.scheduler.actions_table.ActionData;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nonnull;

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
    
    @Nonnull
    public static ActionData createAction(String id, String name, int periodicity, Date lastExecuted) {
        ActionData actionData = new ActionData(id, name, periodicity);
        actionData.setExecutedAt(lastExecuted);
        return actionData;
    }
}
