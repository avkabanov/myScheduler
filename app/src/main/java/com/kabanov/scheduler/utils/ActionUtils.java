package com.kabanov.scheduler.utils;

import com.google.common.base.Strings;
import com.kabanov.scheduler.data.NewAction;
import com.kabanov.scheduler.exceptions.ValidationException;

/**
 * @author Алексей
 * @date 09.12.2018
 */
public class ActionUtils {
    
    public static void validateFieldsNotEmpty(NewAction action) throws ValidationException {
        if (Strings.isNullOrEmpty(action.getName())) {
            throw new ValidationException("Action name can not be empty");
        }

        if (action.getPeriodicityDays() == null) {
            throw new ValidationException("Execution interval can not be empty");
        }

        if (action.getLastExecutedDate() == null) {
            throw new ValidationException("Last executed date can not be empty");
        }
    }
}
