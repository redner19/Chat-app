package com.sololearner.chatapp.utils;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

public class StringUtils {

    //Returns underline text
    public static SpannableString underLine(String str) {
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    public static String getName(String email){
        String[] name = email.split("@");
        return name[0];
    }
}
