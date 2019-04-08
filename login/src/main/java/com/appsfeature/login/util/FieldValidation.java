package com.appsfeature.login.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import com.appsfeature.login.R;

public class FieldValidation {

    private static final String EMPTY = "Empty";

    public static boolean isEmpty(Context context, EditText editText) {
        return hasText(context, editText);
    }
    // check the input field has any text or not
    // return true if it contains text otherwise false
    private static boolean hasText(Context context, EditText editText) {
        return hasText(context, editText, EMPTY);
    }

    private static boolean hasText(Context context, EditText editText, String errorMessage) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {

            SpannableString s = new SpannableString(errorMessage);
            s.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            s.setSpan(new RelativeSizeSpan(1.1f), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//            editText.requestFocus();
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_fail);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            editText.setError(s, drawable);
            return false;
        }
//        Drawable drawable=ContextCompat.getDrawable(context,R.drawable.ic_success);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        editText.setError("done",drawable);
        return true;
    }
}
