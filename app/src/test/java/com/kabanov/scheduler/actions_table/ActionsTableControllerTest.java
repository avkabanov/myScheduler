package com.kabanov.scheduler.actions_table;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.utils.TimeUtils;
import com.kabanov.scheduler.utils.Utils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ActionsTableControllerTest {
    private MainActivity mainActivity = mock(MainActivity.class);
    private ActionsTableView tableView = mock(ActionsTableView.class);
    private ActionsTableController controller = spy(new ActionsTableController(mainActivity, tableView));

    private List<String> actions = new ArrayList<>();
    private Map<String, ActionData> map = new HashMap<>();

    {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ActionData actionData = (ActionData) invocation.getArguments()[0];
                actions.add(actionData.getId());
                map.put(actionData.getId(), actionData);
                return null;
            }
        }).when(tableView).addRow(Matchers.<ActionData>anyObject());

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                String id = (String) invocation.getArguments()[0];
                actions.add(id);
                map.remove(id);
                return null;
            }
        }).when(tableView).removeRow(Matchers.<String>anyObject());

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                int oldIndex = (Integer)invocation.getArguments()[0];
                int newIndex = (Integer)invocation.getArguments()[1];
                Utils.switchElements(oldIndex, newIndex, actions);
                return null;
            }
        }).when(tableView).moveRow(Matchers.anyInt(), Matchers.anyInt());
    }

    @Test
    public void addActionsInNormalOrder() {
        controller.addNewAction(new NewAction("First", 1, new Date()));
        controller.addNewAction(new NewAction("Second", 2, new Date()));
        Assert.assertEquals(getAction(0).getName(), "First");
        Assert.assertEquals(getAction(1).getName(), "Second");
    }

    @Test
    public void addActionsInBackOrder() {
        controller.addNewAction(new NewAction("First", 2, new Date()));
        controller.addNewAction(new NewAction("Second", 1, new Date()));
        Assert.assertEquals(getAction(0).getName(), "Second");
        Assert.assertEquals(getAction(1).getName(), "First");
    }

    @Test
    public void reorderActionsOnUpdate() {
        controller.addNewAction(new NewAction("First", 5, addDays(new Date(),  -10)));     // hardly overdue
        controller.addNewAction(new NewAction("Second", 2, new Date()));                          // not overdue

        // should be sorted like: First, Second. Click on the first one.
        controller.onActionClick(actions.get(0));

        // should be reordered
        Assert.assertEquals("Second", getAction(0).getName());
        Assert.assertEquals("First", getAction(1).getName());
        // check that next execution date is updated
        Assert.assertEquals(
                TimeUtils.cutWithDayAcc(addDays(new Date(), 5)),
                TimeUtils.cutWithDayAcc(getAction(1).getNextExecutionDate()));


        controller.addNewAction(new NewAction("Third", 3, new Date())); // should be located in the middle
        Assert.assertEquals("Second", getAction(0).getName());
        Assert.assertEquals("Third", getAction(1).getName());
        Assert.assertEquals("First", getAction(2).getName());
    }

    @Test
    public void checkNextExecutionDateOnAddAction() {



    }


    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    ActionData getAction(int index) {
        String id = actions.get(index);
        return map.get(id);
    }


}