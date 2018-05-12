package com.kabanov.scheduler;

import android.support.annotation.NonNull;
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
        View actionsTable = inflater.inflate(R.layout.temp, null);
        TableLayout table = actionsTable.findViewById(R.id.table);
        ((ViewGroup) table.getParent()).removeView(table);
        table.removeAllViewsInLayout();
        return table;
    }

    private TableRow getTableRowLayout() {
        View view = activity.getLayoutInflater().inflate(R.layout.temp, null);
        TableRow tableRow = view.findViewById(R.id.table_row);
        ((ViewGroup) tableRow.getParent()).removeView(tableRow);
        return tableRow;
    }

    public void addRow(NewAction action) {
        TableRow tableRow = getTableRowLayout();
        table.addView(tableRow);
    }

    @NonNull
    private TextView addText(String text) {
        TextView textView = new TextView(activity);
        textView.setText(text);
        return textView;
    }

    public TableLayout getActionsTable() {
        return table;
    }

}
