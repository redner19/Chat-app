package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.sololearner.chatapp.R;
import com.sololearner.chatapp.core.User;
import com.sololearner.chatapp.presentation.base.BaseActivity;
import com.sololearner.chatapp.utils.StringUtils;
import com.sololearner.chatapp.viewmodel.LoginViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sololearner.chatapp.utils.NavigationUtils.goToChat;
import static com.sololearner.chatapp.utils.NavigationUtils.goToSignUp;

public class Login extends BaseActivity {

    LoginViewModel loginViewModel;

    //TextInputEditText
    @BindView(R.id.login_user_name_TIET)
    TextInputEditText mUserNameTIET;

    @BindView(R.id.login_password_TIET)
    TextInputEditText mPasswordTIEL;

    //TextInputLayout
    @BindView(R.id.login_user_name_TIL)
    TextInputLayout mUserNameTIL;

    @BindView(R.id.login_password_TIL)
    TextInputLayout mPasswordTIL;

    //TextView
    @BindView(R.id.login_sign_up_btn)
    MaterialTextView mSignUpBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind ButterKnife
        ButterKnife.bind(this);

        initViewModelProvider();

        initButton();

    }

    private void initViewModelProvider() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.isSuccess.observe(this, isSuccess -> {
            if (isSuccess) {
                //Goto Chat Page
                goToChat(this);
            } else {
                mUserNameTIL.setError(getString(R.string.err_value_incorrect));
                mPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });
    }

    private void initButton() {
        //Set text
        mSignUpBTN
                .setText(StringUtils
                        .underLine(getString(R.string.btn_index_sign_up)));
    }


    @OnClick({R.id.login_submit_btn, R.id.login_sign_up_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_submit_btn:
                if (isValid())
                    loginViewModel
                            .loginAccount(getUser());
                break;
            case R.id.login_sign_up_btn:
                goToSignUp(this);
                break;
        }
    }


    private Boolean isValid() {
        boolean valid = true;

        User user = getUser();

        //isValid if email length is greater than equal to and and less than equal to 16
        if (user.isUserNameValid()) {
            mUserNameTIL.setError(null);
            mUserNameTIL.setErrorEnabled(false);
        } else {
            mUserNameTIL.setError(getString(R.string.err_value_incorrect));
            valid = false;
        }

        //isValid if password length is greater than equal to and and less than equal to 16
        if (user.isPasswordValid()) {
            mPasswordTIL.setError(null);
            mPasswordTIL.setErrorEnabled(false);
        } else {
            mPasswordTIL.setError(getString(R.string.err_value_incorrect));
            valid = false;
        }

        return valid;
    }

    private User getUser() {
        String email = Objects.requireNonNull(mUserNameTIET.getText()).toString();
        String password = Objects.requireNonNull(mPasswordTIEL.getText()).toString();
        return new User(email, password);
    }
}