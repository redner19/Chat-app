package com.sololearner.chatapp.presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sololearner.chatapp.R;
import com.sololearner.chatapp.presentation.base.BaseActivity;

public class Host extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_host);
    }
}
