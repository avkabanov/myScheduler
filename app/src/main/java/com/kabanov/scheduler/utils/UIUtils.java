package com.kabanov.scheduler.utils;

import android.widget.EditText;

public class UIUtils {
    
    public static void allowEdit(EditText editText, boolean allowEdit) {
        editText.setCursorVisible(allowEdit);
        editText.setClickable(allowEdit);
        editText.setFocusable(allowEdit);
        editText.setFocusableInTouchMode(allowEdit);
    }
}
