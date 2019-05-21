package com.kabanov.scheduler.test_utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestUtils {
    
    public static void switchElements(int oldIndex, int newIndex, List<String> actionsListInView) {
        if (newIndex == actionsListInView.size()) {
            String id = actionsListInView.remove(oldIndex);
            actionsListInView.add(id);
        } else if (oldIndex != newIndex) {
            actionsListInView.add(newIndex, actionsListInView.remove(oldIndex));
        }
    }
    
    public static Date toDate(String date) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
