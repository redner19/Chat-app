package com.sololearner.chatapp.utils;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import static com.sololearner.chatapp.utils.Constants.MAILINATOR;

public class StringUtils {

    private static boolean INGORE_EMAIL;

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

    public static String replaceUserName(String userName){
        if(userName.contains("@") && !INGORE_EMAIL){
            String[] name = userName.split("@");
            return name[0];
        }else{
            return userName + MAILINATOR;
        }
    }
}
