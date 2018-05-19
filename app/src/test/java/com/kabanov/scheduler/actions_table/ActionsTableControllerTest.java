package com.kabanov.scheduler.actions_table;

import com.kabanov.scheduler.ActionController;
import com.kabanov.scheduler.ActionsControllerImpl;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.TestUtils;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.ValidationException;
import com.kabanov.scheduler.utils.TimeUtils;

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

    private ActionController actionController = new ActionsControllerImpl(mainActivity);
    private ActionsTableController controller = spy(new ActionsTableController(mainActivity, tableView, actionController));

    private List<String> viewActionIds = new ArrayList<>();
    private Map<String, ActionData> mapIdToActionData = new HashMap<>();

    {
        actionController.setActionsTableController(controller);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ActionData actionData = (ActionData) invocation.getArguments()[0];
                viewActionIds.add(actionData.getId());
                mapIdToActionData.put(actionData.getId(), actionData);
                return null;
            }
        }).when(tableView).addRow(Matchers.<ActionData>anyObject());

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                String id = (String) invocation.getArguments()[0];
                viewActionIds.add(id);
                mapIdToActionData.remove(id);
                return null;
            }
        }).when(tableView).removeRow(Matchers.<String>anyObject());

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                int oldIndex = (Integer)invocation.getArguments()[0];
                int newIndex = (Integer)invocation.getArguments()[1];
                TestUtils.switchElements(oldIndex, newIndex, viewActionIds);
                return null;
            }
        }).when(tableView).moveRow(Matchers.anyInt(), Matchers.anyInt());
    }

    @Test
    public void addActionsInNormalOrder() throws ValidationException {
        actionController.addActionRequest(new NewAction("First", 1, new Date()));
        actionController.addActionRequest(new NewAction("Second", 2, new Date()));
        assertViewOrder("First", "Second");
    }

    @Test
    public void addActionsInBackOrder() throws ValidationException {
        actionController.addActionRequest(new NewAction("First", 2, new Date()));
        actionController.addActionRequest(new NewAction("Second", 1, new Date()));

        assertViewOrder("Second", "First");
    }

    @Test
    public void reorderActionsOnUpdate() throws ValidationException {
        actionController.addActionRequest(new NewAction("First", 5, addDays(new Date(),  -10)));     // hardly overdue
        actionController.addActionRequest(new NewAction("Second", 2, new Date()));                          // not overdue

        // should be sorted like: First, Second. Click on the first one.
        assertViewOrder("First", "Second");
        controller.onActionClick(viewActionIds.get(0));

        // should be reordered
        assertViewOrder("Second", "First");

        // check that next execution date is updated
        Assert.assertEquals(
                TimeUtils.cutWithDayAcc(addDays(new Date(), 5)),
                TimeUtils.cutWithDayAcc(getAction(1).getNextExecutionDate()));


        actionController.addActionRequest(new NewAction("Third", 3, new Date())); // should be located in the middle
        assertViewOrder("Second", "Third", "First");
    }

    @Test
    public void reorderingOnClick() throws ValidationException {
        actionController.addActionRequest(new NewAction("Second", 3, new Date()));
        actionController.addActionRequest(new NewAction("Third", 12, new Date()));
        actionController.addActionRequest(new NewAction("First", 43, new Date()));

        assertViewOrder("Second", "Third", "First");
        controller.onActionClick(viewActionIds.get(0));

        assertViewOrder("Second", "Third", "First");
    }


    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    private ActionData getAction(int index) {
        String id = viewActionIds.get(index);
        return mapIdToActionData.get(id);
    }

    private void assertViewOrder(String ...names) {
        for (int i = 0; i < names.length; i++) {
            String expectedName = names[i];
            String actualName = mapIdToActionData.get(viewActionIds.get(i)).getName();

            Assert.assertEquals(expectedName, actualName);
        }
    }

}