package com.kabanov.scheduler.utils;

import android.widget.TextView;

public class UIUtils {
    
    public static void allowEdit(TextView editText, boolean allowEdit) {
        editText.setCursorVisible(allowEdit);
        editText.setClickable(allowEdit);
        editText.setFocusable(allowEdit);
        editText.setFocusableInTouchMode(allowEdit);
    }
}
