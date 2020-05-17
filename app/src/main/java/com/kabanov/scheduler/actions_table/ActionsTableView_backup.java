package com.kabanov.scheduler.actions_table;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Preconditions;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ActionsTableView_backup {

    private static final String OVERDUE_ACTION_COLOR = "#f08f8f";
    private static final String ABOUT_TO_OVERDUE_ACTION_COLOR = "#f6bcbc";
    private static final String NOT_OVERDUE_ACTION_COLOR = "#ECECEC";
    
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");

    private final MainActivity activity;
    private final ActionsTableViewController viewController;
    private final TableLayout table;

    public ActionsTableView_backup(MainActivity activity, ActionsTableViewController viewController) {
        this.activity = activity;
        this.viewController = viewController;
        table = getTableLayout();
    }

    private TableLayout getTableLayout() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View actionsTable = inflater.inflate(R.layout.actions_table_template, null);
        TableLayout table = actionsTable.findViewById(R.id.table);
        ((ViewGroup) table.getParent()).removeView(table);
        table.removeAllViewsInLayout();
        return table;
    }

    private TableRow getTableRowLayout() {
        View view = activity.getLayoutInflater().inflate(R.layout.actions_table_template, null);
        TableRow tableRow = view.findViewById(R.id.table_row);
        ((ViewGroup) tableRow.getParent()).removeView(tableRow);

        tableRow.setLongClickable(true);
        tableRow.setOnLongClickListener(v -> {
            viewController.onActionLongClick(String.valueOf(v.getTag()));
            return true;
        });
        tableRow.setClickable(true);
        tableRow.setOnClickListener(v -> viewController.onActionClick(String.valueOf(v.getTag())));
        return tableRow;
    }

    private TableRow getTableRowDelimiter() {
        View view = activity.getLayoutInflater().inflate(R.layout.actions_table_template, null);
        TableRow tableRow = view.findViewById(R.id.table_row_delimiter);
        ((ViewGroup) tableRow.getParent()).removeView(tableRow);
        return tableRow;
    }

    public void addRow(ActionData action) {
        TableRow tableRow = getTableRowLayout();
        fillTableRow(tableRow, action);
        updateTableRowView(action, tableRow);
        Preconditions.checkNotNull(action.getId());
        tableRow.setTag(action.getId());
        table.addView(tableRow);
        //table.addView(getTableRowDelimiter());
    }

    private void fillTableRow(TableRow tableRow, ActionData action) {
        ((TextView) tableRow.findViewById(R.id.action_name)).setText(action.getName());
        ((TextView) tableRow.findViewById(R.id.action_due_date)).setText(
                formatNextExecutionDate(action.getNextExecutionDate()));
    }

    public TableLayout getActionsTable() {
        return table;
    }

    private String formatNextExecutionDate(Date date) {
        return dateFormatter.format(date);
    }

    public void updateAction(String actionId, ActionData actionData) {
        TableRow tableRow = getActionsTable().findViewWithTag(actionId);
        fillTableRow(tableRow, actionData);
        updateTableRowView(actionData, tableRow);
    }

    private void updateTableRowView(ActionData actionData, TableRow tableRow) {
        if (actionData.isOverdue()) {
            tableRow.setBackgroundColor(Color.parseColor(OVERDUE_ACTION_COLOR));
        } else if (actionData.isAboutToOverdue()) {
            tableRow.setBackgroundColor(Color.parseColor(ABOUT_TO_OVERDUE_ACTION_COLOR));
        } else {
            tableRow.setBackgroundColor(Color.parseColor(NOT_OVERDUE_ACTION_COLOR));
        }
    }

    public void removeRow(String actionId) {
        TableRow tableRow = getActionsTable().findViewWithTag(actionId);
        table.removeView(tableRow);
    }

    public void moveRow(int oldIndex, int newIndex) {
        if (oldIndex == newIndex) return;
        int totalRows = table.getChildCount();
        View child = table.getChildAt(oldIndex);
        if (newIndex == totalRows) {
            table.removeView(child);
            table.addView(child);
        } else if (oldIndex < newIndex) {
            table.removeView(child);
            table.addView(child, newIndex); // because all elements moved
        } else {
            table.removeView(child);
            table.addView(child, newIndex);
        }
    }
}
