package com.sololearner.chatapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sololearner.chatapp.presentation.Chat;
import com.sololearner.chatapp.presentation.Login;
import com.sololearner.chatapp.presentation.SignUp;

public class NavigationUtils {

    public static void goToChat(Context context) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(context, Chat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public static void goToLogin(Context context) {
        Intent intent = new Intent(context, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void goToSignUp(Context context) {
        Intent intent = new Intent(context, SignUp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
