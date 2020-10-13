package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.sololearner.chatapp.R;
import com.sololearner.chatapp.core.User;
import com.sololearner.chatapp.utils.NavigationUtils;
import com.sololearner.chatapp.utils.StringUtils;
import com.sololearner.chatapp.viewmodel.LoginViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

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

    //Button
    @BindView(R.id.login_submit_btn)
    MaterialButton mLoginBTN;

    //TextView
    @BindView(R.id.login_sign_up_btn)
    MaterialTextView mSignUpBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set text
        mSignUpBTN
                .setText(StringUtils
                        .underLine(getString(R.string.btn_index_sign_up)));

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        loginViewModel.isSuccess.observe(requireActivity(), isSuccess -> {
            if (isSuccess) {
                NavigationUtils.navigate(mSignUpBTN,
                        R.id.action_loginFragment_to_chatFragment);
            } else {
                mUserNameTIL.setError(getString(R.string.err_value_incorrect));
                mPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });

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
                NavigationUtils
                        .navigate(mSignUpBTN, R.id.action_loginFragment_to_signupFragment);
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

    private User getUser(){
        String email = Objects.requireNonNull(mUserNameTIET.getText()).toString();
        String password = Objects.requireNonNull(mPasswordTIEL.getText()).toString();
        return new User(email,password);
    }
}