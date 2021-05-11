package com.kabanov.scheduler.actions_table;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.test_utils.ActionTestUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ActionsTableControllerTest {
    private MainActivity mainActivity = mock(MainActivity.class);
    private ActionsTableViewImpl tableView = mock(ActionsTableView.class);

    private UpdateActionViewPresenter updateActionViewPresenter = Mockito.mock(UpdateActionViewPresenter.class);
    private ActionsTableController tableController = spy(
            new ActionsTableController(mainActivity, tableView, updateActionViewPresenter));

    private List<String> viewActionIds = new ArrayList<>();
    private Map<String, ActionData> mapIdToActionData = new HashMap<>();

    {
        Mockito.doAnswer(invocation -> {
            ActionData actionData = (ActionData) invocation.getArguments()[0];
            viewActionIds.add(actionData.getId());
            mapIdToActionData.put(actionData.getId(), actionData);
            return null;
        }).when(tableView).addRow(Matchers.<ActionData>anyObject());

        Mockito.doAnswer(invocation -> {
            String id = (String) invocation.getArguments()[0];
            viewActionIds.add(id);
            mapIdToActionData.remove(id);
            return null;
        }).when(tableView).removeRow(Matchers.<String>anyObject());

       // Mockito.doNothing().when(tableController).showViewActionDialog(any());
       // Mockito.doNothing().when(tableController).showEditActionDialog(any());
    }

    @Test
    public void addActionsInNormalOrder() {
        tableController.addNewAction(ActionTestUtils.createAction("First", 1, new Date()));
        tableController.addNewAction(ActionTestUtils.createAction("Second", 2, new Date()));
        assertViewOrder("First", "Second");
    }

    @Test
    public void openViewDialogOnClick() {
        ActionData thirdAction = ActionTestUtils.createAction("Third", 12, new Date());

        tableController.addNewAction(ActionTestUtils.createAction("Second", 3, new Date()));
        tableController.addNewAction(thirdAction);
        tableController.addNewAction(ActionTestUtils.createAction("First", 43, new Date()));

        assertViewOrder("Second", "Third", "First");
        tableController.onActionClick(viewActionIds.get(1));

        ArgumentCaptor<ActionData> argument = ArgumentCaptor.forClass(ActionData.class);
        // TODO uncomment Mockito.verify(tableController).showViewActionDialog(argument.capture());
        Assert.assertEquals(thirdAction, argument.getValue());
    }

    @Test
    public void openEditDialogOnClick() {
        ActionData thirdAction = ActionTestUtils.createAction("Third", 12, new Date());

        tableController.addNewAction(ActionTestUtils.createAction("Second", 3, new Date()));
        tableController.addNewAction(thirdAction);
        tableController.addNewAction(ActionTestUtils.createAction("First", 43, new Date()));

        assertViewOrder("Second", "Third", "First");
        tableController.onActionLongClick(viewActionIds.get(1));

        ArgumentCaptor<ActionData> argument = ArgumentCaptor.forClass(ActionData.class);
        Mockito.verify(tableController).showEditActionDialog(argument.capture());
        Assert.assertEquals(thirdAction, argument.getValue());
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

    private void assertViewOrder(String... names) {
        for (int i = 0; i < names.length; i++) {
            String expectedName = names[i];
            String actualName = mapIdToActionData.get(viewActionIds.get(i)).getName();

            Assert.assertEquals(expectedName, actualName);
        }
    }
}