package com.kabanov.scheduler;

import com.google.common.collect.Lists;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.actions_table.UpdateActionViewPresenter;
import com.kabanov.scheduler.actions_table.UpdateActionViewPresenterImpl;
import com.kabanov.scheduler.test_utils.ActionTestUtils;
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
        ActionData oldAction = ActionTestUtils.createAction("first", 1, new Date());
        
        actionsController.setActionsList(Lists.newArrayList(oldAction));
        ActionData newAction = ActionTestUtils.createAction(oldAction.getId(), "second", 2, TimeUtils.addDays(new Date(), -2));
        actionsTableController.updateAction(oldAction.getId(), newAction);

        ArgumentCaptor<ActionData> actionCaptor = ArgumentCaptor.forClass(ActionData.class);
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(actionsTableController).updateAction(idCaptor.capture(), actionCaptor.capture());
        Assert.assertEquals(newAction, actionCaptor.getValue());
    }

    @Test
    public void shouldUpdateActionOnCompleteBtnClick() {
        Date now = new Date();
        Date expected = TimeUtils.addDays(now, 1);
        ActionData action = ActionTestUtils.createAction("first", 1, now);
        
        // TODO fix tests
        //actionsController.addActionRequest(action);

        // TODO fix tests
        // updateActionViewPresenter.onActionCompleteBtnPressed(action.getId());

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
        // TODO fix tests
        // actionsController.addActionRequest(action);
        
        // TODO fix tests
        // updateActionViewPresenter.onActionDeleteBtnPressed(action.getId());
        
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(actionsTableController).removeAction(stringCaptor.capture());
        Assert.assertEquals(action.getId(), stringCaptor.getValue());
    }
}