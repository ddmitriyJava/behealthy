package com.example.behealthy.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.behealthy.R;

public class EditTextListener implements TextWatcher {
    EditText editText;

    public EditTextListener(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.setBackgroundResource(R.drawable.edit_text_black_style);

    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
