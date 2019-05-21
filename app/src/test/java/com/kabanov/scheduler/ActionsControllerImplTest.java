package com.kabanov.scheduler;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenterImpl;
import com.kabanov.scheduler.test_utils.ActionTestUtils;
import com.kabanov.scheduler.test_utils.TestUtils;
import com.kabanov.scheduler.utils.TimeUtils;

/**
 * @author Алексей
 * @date 21.05.2019
 */
public class ActionsControllerImplTest {

    private ActionsTableController actionsTableController = Mockito.mock(ActionsTableController.class);
    private ActionsControllerImpl actionsController = new ActionsControllerImpl();
    {
        actionsController.setActionsTableController(actionsTableController);
    }
    private UpdateActionViewPresenter updateActionViewPresenter = new UpdateActionViewPresenterImpl(actionsController);
    
    @Test
    public void shouldUpdateActionOnUpdateBtnClick() {
        ActionData action = ActionTestUtils.createAction("first", 1, new Date());
        actionsController.addActionRequest(action);

        action.setName("second");
        action.setPeriodicityDays(2);
        Date newTime = TimeUtils.addDays(new Date(), -2); 
        action.setExecutedAt(newTime);
        updateActionViewPresenter.onActionUpdateBtnPressed(action.getId(), action);

        ArgumentCaptor<ActionData> actionCaptor = ArgumentCaptor.forClass(ActionData.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(actionsTableController).updateAction(stringCaptor.capture(), actionCaptor.capture());
        Assert.assertEquals(action.getId(), stringCaptor.getValue());
        Assert.assertEquals(action, actionCaptor.getValue());
    }

    @Test
    public void shouldUpdateActionOnCompleteBtnClick() {
        ActionData action = ActionTestUtils.createAction("first", 1, TestUtils.toDate("20.01.2008"));
        actionsController.addActionRequest(action);

        updateActionViewPresenter.onActionCompleteBtnPressed(action.getId());

        ArgumentCaptor<ActionData> actionCaptor = ArgumentCaptor.forClass(ActionData.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(actionsTableController).updateAction(stringCaptor.capture(), actionCaptor.capture());
        Assert.assertEquals(action.getId(), stringCaptor.getValue());
        Assert.assertEquals(TestUtils.toDate("21.01.2008"), action.getNextExecutionDate());
    }
}