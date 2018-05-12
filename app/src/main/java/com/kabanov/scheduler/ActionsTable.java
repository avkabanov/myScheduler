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
        View actionsTable = inflater.inflate(R.layout.actions_table, null);
        TableLayout table = actionsTable.findViewById(R.id.table);
        ((ViewGroup) table.getParent()).removeView(table);
        return table;
    }

    private TableRow getTableRowLayout() {
        View view = activity.getLayoutInflater().inflate(R.layout.actions_table_row, null);
        TableRow tableRow = view.findViewById(R.id.table_row);
        ((ViewGroup) tableRow.getParent()).removeView(tableRow);
        return tableRow;
    }

    public void addRow(NewAction action) {

        {
            table.addView(getTableRowLayout());
        }

        //table.addView(createTableRow(action));
    }

/*
    private View createTableRow(NewAction action) {


        TableRow tableRow = new TableRow(activity);
        tableRow.setBackgroundColor(Color.BLUE);
        tableRow.setMinimumHeight(500);
        return tableRow;

    }

*/
    private View createTableRow(NewAction action) {
        View view = LayoutInflater.from(activity).inflate(R.layout.actions_table_row, null);
        View tableRow = view.findViewById(R.id.table_row);
        ((ViewGroup)tableRow.getParent()).removeView(tableRow);


        LayoutInflater inflater = activity.getLayoutInflater();
        View actionsTableRowLayout = inflater.inflate(R.layout.actions_table_row, null);
        //TableRow tableRow = actionsTableRowLayout.findViewById(R.id.table_row);
        //((TextView)rowView.findViewById(R.id.name)).setText(action.getName());
//        ((TextView)rowView.findViewById(R.id.finish_time)).setText(action.getDates());
        return tableRow;
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
