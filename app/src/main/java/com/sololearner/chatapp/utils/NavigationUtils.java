package com.sololearner.chatapp.utils;

import android.view.View;

import androidx.navigation.Navigation;

public class NavigationUtils {
    public static void navigate(View view , int id){
        Navigation.findNavController(view).navigate(id);
    }
}
