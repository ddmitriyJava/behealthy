package com.example.behealthy.util;

import android.widget.EditText;
import android.widget.TextView;

public class ViewManager {
    public static String getContent(EditText editText){
        return editText.getText().toString();
    }

    public static String getContent(TextView textView){
        return textView.getText().toString();
    }

    public static void setContent(TextView textView, String content){
        textView.setText(content);
    }
}
