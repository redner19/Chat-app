package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.sololearner.chatapp.R;
import com.sololearner.chatapp.core.User;
import com.sololearner.chatapp.utils.NavigationUtils;
import com.sololearner.chatapp.utils.StringUtils;
import com.sololearner.chatapp.viewmodel.FragmentsViewModel;
import com.sololearner.chatapp.viewmodel.SignUpViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends Fragment {

    private SignUpViewModel signUpViewModel;

    //TextInputEditText
    @BindView(R.id.sign_up_user_name_TIET)
    TextInputEditText mUserNameTIET;

    @BindView(R.id.sign_up_password_TIET)
    TextInputEditText mPasswordTIEL;

    //TextInputLayout
    @BindView(R.id.sign_up_user_name_TIL)
    TextInputLayout mUserNameTIL;

    @BindView(R.id.sign_up_password_TIL)
    TextInputLayout mPasswordTIL;

    //Button
    @BindView(R.id.sign_up_submit_btn)
    MaterialButton mSignUpBTN;

    //TextView
    @BindView(R.id.sign_up_login_btn)
    MaterialTextView mLoginBTN;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoginBTN.setText(StringUtils.underLine(getString(R.string.btn_index_login)));

        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        signUpViewModel.isSuccess.observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                NavigationUtils.navigate(mLoginBTN,
                        R.id.action_signupFragment_to_chatFragment);
            } else {
                mUserNameTIL.setError(getString(R.string.err_value_incorrect));
                mPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });
    }

    @OnClick({R.id.sign_up_submit_btn, R.id.sign_up_login_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_submit_btn:
                if (isValid())
                    signUpViewModel.createAccount(getUser());
                break;
            case R.id.sign_up_login_btn:
                //navigate to login fragment
                NavigationUtils
                        .navigate(mLoginBTN, R.id.action_signupFragment_to_loginFragment);
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