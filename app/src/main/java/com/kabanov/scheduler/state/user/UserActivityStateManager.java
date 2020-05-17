package com.kabanov.scheduler.state.user;

import com.kabanov.scheduler.ActionController;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.add_action.ValidationException;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.xml.XmlParser;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class UserActivityStateManager {
    private AppCompatActivity activity;
    private UserStateManager userStateManager;
    private ActionController actionController;
    private XmlParser xmlParser = new XmlParser();
    

    public UserActivityStateManager(AppCompatActivity activity,
                                    ActionController actionController) {
        this.activity = activity;
        userStateManager = new UserStateManager(activity);
        this.actionController = actionController;
    }

    public void exportUserState(ApplicationState applicationState) {
        userStateManager.exportUserState(applicationState);
    }

    public void requestImportUserState() {
        userStateManager.requestImportUserState();
    }

    public void onImportUserStateFinished(String state) {

        ApplicationState userState = null; 
        try {
            userState = xmlParser.format(state);
            actionController.clearAll();
            for (ActionData actionData : userState.getActions()) {
                try {
                    actionController.addActionRequest(actionData);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toast.makeText(activity, "Import failed", Toast.LENGTH_LONG).show();
        }
    }
}
