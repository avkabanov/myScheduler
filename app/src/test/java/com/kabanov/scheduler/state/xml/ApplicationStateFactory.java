package com.kabanov.scheduler.state.xml;

import java.util.Arrays;
import java.util.List;

import com.kabanov.scheduler.state.data.ActionDataState;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.utils.TimeUtilsTest;

public class ApplicationStateFactory {

    public static ApplicationState applicationState00() {
        ApplicationState state = new ApplicationState();

        List<ActionDataState> actionList = Arrays.asList(
                new ActionDataState("action1", "name1", 20, TimeUtilsTest.toDate("2020-05-01")),
                new ActionDataState("action2", "name2", 19, TimeUtilsTest.toDate("2020-05-17"))
        );
        state.setActionDataStateList(actionList);
        return state;
    }

    public static ApplicationState applicationState01() {
        ApplicationState state = new ApplicationState();

        List<ActionDataState> actionList = Arrays.asList(
                new ActionDataState("action1", "firstActionName", 45, TimeUtilsTest.toDate("2020-02-09")),
                new ActionDataState("action2", "secondActionName", 360, TimeUtilsTest.toDate("2019-04-15"))
        );
        state.setActionDataStateList(actionList);
        return state;
    }
}
