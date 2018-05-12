package com.kabanov.scheduler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kabanov.scheduler.add_action.NewAction;

public class ActionsTable {

    private MainActivity activity;
    private TableLayout table;
    private View view;


    public ActionsTable(MainActivity activity) {
        this.activity = activity;


        LayoutInflater inflater = activity.getLayoutInflater();
        View actionsTable = inflater.inflate(R.layout.actions_table, null);
        table = actionsTable.findViewById(R.id.table);
        ((ViewGroup) table.getParent()).removeView(table);

        {
          /*  View view = activity.getLayoutInflater().inflate(R.layout.temp2, null);
            Button button = view.findViewById(R.id.button);
            ((ViewGroup) button.getParent()).removeView(button);


            LinearLayout content = activity.findViewById(R.id.content_main_layout);
            content.addView(button);*/
            //contentMain.addView(button);

        /*LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.actions_table, null);
        table = view.findViewById(R.id.table);*/
        }
    }

    public void addRow(NewAction action) {

        {
            View view = activity.getLayoutInflater().inflate(R.layout.actions_table_row, null);
            TableRow tableRow = view.findViewById(R.id.table_row);
            ((ViewGroup) tableRow.getParent()).removeView(tableRow);


            LinearLayout contentMain = activity.findViewById(R.id.table);
            contentMain.addView(tableRow);
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
