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
import com.sololearner.chatapp.utils.AppUtils;
import com.sololearner.chatapp.utils.StringUtils;
import com.sololearner.chatapp.viewmodel.LoginViewModel;

public class Login extends Fragment {

    private LoginViewModel mLoginViewModel;

    private CommonLayoutBinding mLoginView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mLoginView = CommonLayoutBinding.inflate(inflater, container, false);
        // init viewModel
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        return mLoginView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initViewModelObserver();

        initFragmentViews();
    }


    private void initViewModelObserver() {
        // observe if login is success viewModel
        mLoginViewModel.isSuccess.observe(getViewLifecycleOwner(), isSuccess -> {
            // this will close the loading screen
            Navigation.findNavController(mLoginView.view7).popBackStack();

            if (isSuccess) {
                // navigate to chat fragment
                NavDirections action = LoginDirections.actionLoginToChat();
                Navigation.findNavController(mLoginView.view7).navigate(action);
            } else {
                mLoginView.commonUserNameTIL.setError(getString(R.string.err_value_incorrect));
                mLoginView.commonPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });

        // isValid if email length is greater than equal to and and less than equal to 16
        mLoginViewModel.isEmailValid().observe(getViewLifecycleOwner(), isValid -> {
            if (isValid) {
                mLoginView.commonUserNameTIL.setError(null);
                mLoginView.commonUserNameTIL.setErrorEnabled(false);
            } else {
                mLoginView.commonUserNameTIL.setError(getString(R.string.err_value_incorrect));
            }
        });

        // isValid if password length is greater than equal to and and less than equal to 16
        mLoginViewModel.isPasswordValid().observe(getViewLifecycleOwner(), isValid -> {
            if (isValid) {
                mLoginView.commonPasswordTIL.setError(null);
                mLoginView.commonPasswordTIL.setErrorEnabled(false);
            } else {
                mLoginView.commonPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });
    }

    private void initFragmentViews() {
        // set text
        mLoginView.commonNavBtn
                .setText(StringUtils
                        .underLine(getString(R.string.btn_index_sign_up)));
        mLoginView.commonSubmitBtn
                .setText(getString(R.string.btn_index_login));

        mLoginView.commonSubmitBtn.setOnClickListener(v -> {
            // check if input is valid
            if (mLoginViewModel.validateInputUser(getUser())){
                // show loading screen
                NavDirections action = LoginDirections.actionLoginToProgressDialog();
                Navigation.findNavController(v).navigate(action);

                // login account if input is valid
                mLoginViewModel
                        .loginAccount(getUser());

                // close/hide keyboard
                AppUtils
                        .hideKeyboardFrom(requireContext(),mLoginView.commonSubmitBtn);
            }
        });
        mLoginView.commonNavBtn.setOnClickListener(v -> {
            // navigate to sign up fragment
            NavDirections action = LoginDirections.actionLoginToSignUp();
            Navigation.findNavController(v).navigate(action);
        });
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