package com.kabanov.scheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kabanov.scheduler.add_action.NewAction;

public class ActionsTable {

    private MainActivity activity;
    private TableLayout table;


    public ActionsTable(MainActivity activity) {
        this.activity = activity;
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
        return tableRow;
    }

    public void addRow(NewAction action) {
        TableRow tableRow = getTableRowLayout();
        filTableRow(tableRow, action);
        table.addView(tableRow);
    }

    private void filTableRow(TableRow tableRow, NewAction action) {
        ((TextView)tableRow.findViewById(R.id.action_name)).setText(action.getName());
        ((TextView)tableRow.findViewById(R.id.action_due_date)).setText(String.valueOf(action.getDates()));
    }

    public TableLayout getActionsTable() {
        return table;
    }
}
