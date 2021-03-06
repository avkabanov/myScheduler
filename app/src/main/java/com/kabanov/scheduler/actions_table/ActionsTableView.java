package com.kabanov.scheduler.actions_table;

import android.widget.ListView;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;
import java.util.ArrayList;
import java.util.List;

public class ActionsTableView implements ActionsTableViewImpl {
    private final List<ActionData> list = new ArrayList<>();
    private final ActionDataAdapter adapter;
    
    public ActionsTableView(MainActivity activity, ActionsTableViewController viewController) {
        ListView listView = activity.findViewById(R.id.listView);

        adapter = new ActionDataAdapter(activity, list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            ActionData action = adapter.getItem(position);
            viewController.onActionClick(action.getId());
        });
        listView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            ActionData action = adapter.getItem(position);
            viewController.onActionLongClick(action.getId());
            return true;
        });

    }

    @Override
    public void addRow(ActionData action) {
        list.add(action);
        adapter.sort();
    }

    @Override
    public void updateAction(String actionId, ActionData actionData) {
        int index = findActionIdIndex(actionId);
        list.set(index, actionData);
        adapter.sort();
    }

    private int findActionIdIndex(String actionId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(actionId)) {
                return i;
            }
        }
        throw new IllegalStateException("Error getting action with not existing id: " + actionId);
    }

    @Override
    public void removeRow(String actionId) {
        int index = findActionIdIndex(actionId);
        list.remove(index);
        adapter.sort();
    }
}
