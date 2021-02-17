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
import com.sololearner.chatapp.viewmodel.SignUpViewModel;

public class SignUp extends Fragment {

    private SignUpViewModel mSignUpViewModel;

    private CommonLayoutBinding mSignUpView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mSignUpView = CommonLayoutBinding.inflate(inflater, container,false);

        return mSignUpView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initViewModelProvider();

        initFragmentViews();
    }


    private void initViewModelProvider() {
        // init viewModel
        mSignUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        // observe if sign-up is success viewModel
        mSignUpViewModel.isSuccess.observe(getViewLifecycleOwner(), isSuccess -> {
            // this will close the loading screen
            Navigation.findNavController(mSignUpView.view7).popBackStack();

            if (isSuccess) {
                // navigate to chat
                NavDirections action = SignUpDirections.actionSignUpToChat();
                Navigation.findNavController(mSignUpView.view7).navigate(action);
            } else {
                mSignUpView.commonUserNameTIL.setError(getString(R.string.err_value_incorrect));
                mSignUpView.commonPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });


        //isValid if email length is greater than equal to and and less than equal to 16
        mSignUpViewModel.isEmailValid().observe(getViewLifecycleOwner(), isValid -> {
            if (isValid) {
                mSignUpView.commonUserNameTIL.setError(null);
                mSignUpView.commonUserNameTIL.setErrorEnabled(false);
            } else {
                mSignUpView.commonUserNameTIL.setError(getString(R.string.err_value_incorrect));
            }
        });

        //isValid if password length is greater than equal to and and less than equal to 16
        mSignUpViewModel.isPasswordValid().observe(getViewLifecycleOwner(), isValid -> {
            if (isValid) {
                mSignUpView.commonPasswordTIL.setError(null);
                mSignUpView.commonPasswordTIL.setErrorEnabled(false);
            } else {
                mSignUpView.commonPasswordTIL.setError(getString(R.string.err_value_incorrect));
            }
        });
    }

    private void initFragmentViews() {
        // Set text
        mSignUpView.commonNavBtn
                .setText(StringUtils
                        .underLine(getString(R.string.btn_index_login)));

        mSignUpView.commonSubmitBtn
                .setText(getString(R.string.btn_index_sign_up));

        mSignUpView.commonSubmitBtn.setOnClickListener(v -> {
            // check if input is valid
            if (mSignUpViewModel.validateInputUser(getUser())){
                // show loading screen
                NavDirections action = SignUpDirections.actionSignUpToProgressDialog();
                Navigation.findNavController(v).navigate(action);

                // create account if input is valid
                mSignUpViewModel.createAccount(getUser());
                // close/hide keyboard
                AppUtils
                        .hideKeyboardFrom(requireContext(),mSignUpView.commonSubmitBtn);
            }

        });

        mSignUpView.commonNavBtn.setOnClickListener(v -> {
            //navigate to login fragment
            NavDirections action = SignUpDirections.actionSignUpToLogin();
            Navigation.findNavController(v).navigate(action);

        });

    }


    private User getUser() {

        String email = mSignUpView.commonUserNameTIET.getText().toString();

        String password = mSignUpView.commonPasswordTIET.getText().toString();

        return new User(email, password);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        mSignUpView = null;
    }
}