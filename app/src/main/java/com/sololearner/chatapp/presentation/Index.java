package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.sololearner.chatapp.R;
import com.sololearner.chatapp.presentation.base.BaseActivity;
import com.sololearner.chatapp.viewmodel.FragmentsViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sololearner.chatapp.utils.NavigationUtils.goToChat;
import static com.sololearner.chatapp.utils.NavigationUtils.goToLogin;
import static com.sololearner.chatapp.utils.NavigationUtils.goToSignUp;


public class Index extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        ButterKnife.bind(this);

        initViewModelProvider();
    }

    private void initViewModelProvider() {
        FragmentsViewModel loginViewModel = new ViewModelProvider(this).get(FragmentsViewModel.class);
        loginViewModel.isLogin.observe(this, isLogin -> {
            if (isLogin)
                goToChat(this);
        });
    }


    @OnClick({R.id.sign_up_id, R.id.login_id})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_id:
                //navigate to login fragment
                goToLogin(this);
                break;
            case R.id.sign_up_id:
                //navigate to sign up fragment
                goToSignUp(this);
                break;
        }
    }
}