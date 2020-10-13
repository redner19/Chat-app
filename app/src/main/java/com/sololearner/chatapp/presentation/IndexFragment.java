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
import com.sololearner.chatapp.R;
import com.sololearner.chatapp.utils.NavigationUtils;
import com.sololearner.chatapp.viewmodel.FragmentsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class IndexFragment extends Fragment {

    @BindView(R.id.sign_up_id)
    MaterialButton mSignUp;

    @BindView(R.id.login_id)
    MaterialButton mLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);

        // Bind this view
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentsViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(FragmentsViewModel.class);
        loginViewModel.isLogin.observe(getViewLifecycleOwner(), isLogin ->{
            if (isLogin)
                NavigationUtils
                        .navigate(mSignUp,R.id.action_indexFragment_to_chatFragment);
        });
    }


    @OnClick({R.id.sign_up_id,R.id.login_id})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.login_id :
                //navigate to login fragment
                NavigationUtils
                        .navigate(mLogin,R.id.action_indexFragment_to_loginFragment);
                break;
            case R.id.sign_up_id :
                //navigate to sign up fragment
                NavigationUtils
                        .navigate(mSignUp,R.id.action_indexFragment_to_signupFragment);
                break;
        }
    }
}