package com.kabanov.scheduler.actions_table;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActionsTableView {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");

    private MainActivity activity;
    private TableLayout table;


    public ActionsTableView(MainActivity activity) {
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

        tableRow.setLongClickable(true);
        tableRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
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
        filTableRow(tableRow, action);
        Preconditions.checkNotNull(action.getId());
        tableRow.setTag(action.getId());
        table.addView(tableRow);
        table.addView(getTableRowDelimiter());
    }

    private void filTableRow(TableRow tableRow, ActionData action) {
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
}
