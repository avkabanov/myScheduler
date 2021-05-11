package com.kabanov.scheduler;

import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenterImpl;
import com.kabanov.scheduler.test_utils.ActionTestUtils;
import com.kabanov.scheduler.test_utils.AssertUtils;
import com.kabanov.scheduler.utils.TimeUtils;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

/**
 * @author Алексей
 * @date 21.05.2019
 */
public class ActionsControllerImplTest {

    private ActionsTableController actionsTableController = mock(ActionsTableController.class);
    private ActionsControllerImpl actionsController = new ActionsControllerImpl(mock(MainActivity.class));
    {
        actionsController.setActionsTableController(actionsTableController);
    }
    private UpdateActionViewPresenter updateActionViewPresenter = new UpdateActionViewPresenterImpl(actionsController);
    
    @Test
    public void shouldUpdateActionOnUpdateBtnClick() {
        ActionData action = ActionTestUtils.createAction("first", 1, new Date());
        actionsController.addActionRequest(action);

        action.setName("Second");
        action.setPeriodicityDays(2);
        Date newTime = TimeUtils.addDays(new Date(), -2); 
        action.setExecutedAt(newTime);
        updateActionViewPresenter.onActionUpdateBtnPressed(action.getId(), action);

        ArgumentCaptor<ActionData> actionCaptor = ArgumentCaptor.forClass(ActionData.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(actionsTableController).updateAction(stringCaptor.capture(), actionCaptor.capture());
        Assert.assertEquals(action.getId(), stringCaptor.getValue());
        Assert.assertEquals("Second", actionCaptor.getValue().getName());
        Assert.assertEquals(2, actionCaptor.getValue().getPeriodicityDays());
        AssertUtils.assertEqualsDate(newTime, actionCaptor.getValue().getLastExecutionDate());
    }

    @Test
    public void shouldUpdateActionOnCompleteBtnClick() {
        Date now = new Date();
        Date expected = TimeUtils.addDays(now, 1);
        ActionData action = ActionTestUtils.createAction("first", 1, now);
        actionsController.addActionRequest(action);

        updateActionViewPresenter.onActionCompleteBtnPressed(action.getId());

        ArgumentCaptor<ActionData> actionCaptor = ArgumentCaptor.forClass(ActionData.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(actionsTableController).updateAction(stringCaptor.capture(), actionCaptor.capture());
        Assert.assertEquals(action.getId(), stringCaptor.getValue());
        Assert.assertEquals(
                TimeUtils.cutWithDayAcc(expected), 
                TimeUtils.cutWithDayAcc(actionCaptor.getValue().getNextExecutionDate()));
    }

    @Test
    public void shouldRemoveActionOnRemoveBtnClick() {
        ActionData action = ActionTestUtils.createAction("first", 1, new Date());
        actionsController.addActionRequest(action);
        
        updateActionViewPresenter.onActionDeleteBtnPressed(action.getId());
        
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(actionsTableController).removeAction(stringCaptor.capture());
        Assert.assertEquals(action.getId(), stringCaptor.getValue());
    }
}