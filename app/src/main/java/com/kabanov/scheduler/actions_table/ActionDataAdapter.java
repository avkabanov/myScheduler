package com.kabanov.scheduler.actions_table;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.kabanov.scheduler.R;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionDataAdapter extends ArrayAdapter <ActionData> {

    private static final String DUE_DATE_NOT_OVERDUE_TEXT_COLOR = "#737373";
    private static final String DUE_DATE_OVERDUE_TEXT_COLOR = "#ff0000";     

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");
    
    public ActionDataAdapter(@NonNull Context context, @NonNull List<ActionData> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ActionData action = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.action_table_row, parent, false);
        }

        fillTableRow(convertView, action);
        updateTableRowView(action, convertView);
        
        return convertView;
    }

    private void fillTableRow(View view, ActionData action) {
        ((TextView) view.findViewById(R.id.action_name)).setText(action.getName());
        ((TextView) view.findViewById(R.id.action_due_date)).setText(
                formatNextExecutionDate(action.getNextExecutionDate()));
    }

    private String formatNextExecutionDate(Date date) {
        return dateFormatter.format(date);
    }

    private void updateTableRowView(ActionData actionData, View tableRow) {
        ImageView image = tableRow.findViewById(R.id.table_row_icon);
        TextView dueToText = tableRow.findViewById(R.id.action_due_date);

        if (actionData.isOverdue()) {
            image.setImageResource(R.drawable.alarm_overdue_255_100_100);
            image.setVisibility(View.VISIBLE);
            
            dueToText.setTextColor(Color.parseColor(DUE_DATE_OVERDUE_TEXT_COLOR));
        } else if (actionData.isAboutToOverdue()) {
            image.setImageResource(R.drawable.sandglass_115_115_115);
            image.setVisibility(View.VISIBLE);

            dueToText.setTextColor(Color.parseColor(DUE_DATE_NOT_OVERDUE_TEXT_COLOR));
        } else {
            image.setVisibility(View.INVISIBLE);
            dueToText.setTextColor(Color.parseColor(DUE_DATE_NOT_OVERDUE_TEXT_COLOR));
        }
    }

    public void sort() {
        sort(Comparator.comparing(ActionData::getNextExecutionDate));
    }
}
