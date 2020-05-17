package com.kabanov.scheduler.state.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.state.data.ApplicationState;

import junit.framework.Assert;

public class XmlParserTest {

    private XmlParser xmlParser = new XmlParser();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void shouldFormatStateWhenValidStateIsGiven() throws Exception {

        ApplicationState result = xmlParser.format(state);

        List<ActionData> actualActions = result.getActions();
        Assert.assertEquals(2, actualActions.size());

        ActionData firstAction = getActionWithId("action1", actualActions);
        assertAction(firstAction, "action1", 45, "2020-02-09");

        ActionData secondAction = getActionWithId("action2", actualActions);
        assertAction(secondAction, "action2", 360, "2019-04-15");

    }

    private void assertAction(ActionData action, String id, int periodicity, String lastExecutionDate) throws ParseException {
        Assert.assertEquals(id, action.getId());
        Assert.assertEquals(periodicity, action.getPeriodicityDays());
        Assert.assertEquals(simpleDateFormat.parse(lastExecutionDate), action.getLastExecutionDate());

    }

    private ActionData getActionWithId(String actionId, List<ActionData> actualActions) {
        return actualActions.stream().filter(it -> it.getId().equals(actionId)).findAny().orElse(null);
    }

    private String state = "<ApplicationState>\n" +
            "    <ActionDataStateList class=\"java.util.ArrayList\">\n" +
            "        <actionDataState><id>action1</id><name>firstActionName</name><periodicityDays>45</periodicityDays><lastExecutionDate>2020-02-09</lastExecutionDate></actionDataState>\n" +
            "        <actionDataState><id>action2</id><name>secondActionName</name><periodicityDays>360</periodicityDays><lastExecutionDate>2019-04-15</lastExecutionDate></actionDataState>\n" +
            "    </ActionDataStateList>\n" +
            "</ApplicationState>";
}