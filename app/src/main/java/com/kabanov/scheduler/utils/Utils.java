package com.kabanov.scheduler.utils;

import java.util.List;

public class Utils {
    public static void switchElements(int oldIndex, int newIndex, List<String> actionsListInView) {
        if (newIndex == actionsListInView.size()) {
            String id = actionsListInView.remove(oldIndex);
            actionsListInView.add(id);
        } else if (oldIndex != newIndex) {
            actionsListInView.add(newIndex, actionsListInView.remove(oldIndex));
        }
    }
}
