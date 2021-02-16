package com.sololearner.chatapp.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.sololearner.chatapp.R;
import com.sololearner.chatapp.core.User;
import com.sololearner.chatapp.databinding.CommonLayoutBinding;
import com.sololearner.chatapp.utils.StringUtils;
import com.sololearner.chatapp.viewmodel.LoginViewModel;

public class Login extends Fragment {

    private LoginViewModel mLoginViewModel;

    private CommonLayoutBinding mLoginView;

    //TODO transfer some logic in viewModel


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoginView = CommonLayoutBinding.inflate(inflater,container,false);
        return mLoginView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModelProvider();

        initButton();
    }

    private void initViewModelProvider() {
        // init viewModel
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // observe if login is success viewModel
        mLoginViewModel.isSuccess.observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                //navigate to chat fragment
                NavDirections action = LoginDirections.actionLoginToChat();
                Navigation.findNavController(mLoginView.view7).navigate(action);
            } else {
                mLoginView.commonUserNameTIL.setError(getString(R.string.err_value_incorrect));
                mLoginView.commonPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });
    }

    private void initButton() {
        //Set text
        mLoginView.commonNavBtn
                .setText(StringUtils
                        .underLine(getString(R.string.btn_index_sign_up)));
        mLoginView.commonSubmitBtn
                .setText(getString(R.string.btn_index_login));

        mLoginView.commonSubmitBtn.setOnClickListener(v -> {
            // check if input is valid
            if (isValid())
                // login account if input is valid
                mLoginViewModel
                        .loginAccount(getUser());
        });
        mLoginView.commonNavBtn.setOnClickListener(v -> {
                //navigate to sign up fragment
            NavDirections action = LoginDirections.actionLoginToSignUp();
            Navigation.findNavController(v).navigate(action);
        });
    }


    private Boolean isValid() {
        User user = getUser();

        //isValid if email length is greater than equal to and and less than equal to 16
        if (user.isUserNameValid()) {
            mLoginView.commonUserNameTIL.setError(null);
            mLoginView.commonUserNameTIL.setErrorEnabled(false);
        } else {
            mLoginView.commonUserNameTIL.setError(getString(R.string.err_value_incorrect));
            return false;
        }

        //isValid if password length is greater than equal to and and less than equal to 16
        if (user.isPasswordValid()) {
            mLoginView.commonPasswordTIL.setError(null);
            mLoginView.commonPasswordTIL.setErrorEnabled(false);
        } else {
            mLoginView.commonPasswordTIL.setError(getString(R.string.err_value_incorrect));
            return false;
        }

        return true;
    }

    private User getUser() {
        String email = mLoginView.commonUserNameTIET.getText().toString();
        String password = mLoginView.commonPasswordTIET.getText().toString();
        return new User(email, password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoginView = null;
    }
}