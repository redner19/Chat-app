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

import com.sololearner.chatapp.databinding.ActivityIndexBinding;
import com.sololearner.chatapp.viewmodel.FragmentsViewModel;


public class Index extends Fragment {

    private FragmentsViewModel mViewModel;

    private ActivityIndexBinding mIndexView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mIndexView = ActivityIndexBinding.inflate(inflater, container, false);

        return mIndexView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(FragmentsViewModel.class);

        initViewModelProvider();

        initFragmentButtons();

    }

    private void initFragmentButtons() {
        mIndexView.btnLogin.setOnClickListener(v -> {
            //navigate to login fragment
            NavDirections action = IndexDirections.actionIndexToLogin();
            Navigation.findNavController(v).navigate(action);
        });

        mIndexView.btnSignUp.setOnClickListener(v -> {
            //navigate to sign up fragment
            NavDirections action = IndexDirections.actionIndexToSignUp();
            Navigation.findNavController(v).navigate(action);
        });


    }

    private void initViewModelProvider() {
        mViewModel.isLogin.observe(getViewLifecycleOwner(), isLogin -> {
            if (isLogin) {
                //navigate to chat fragment
                NavDirections action = IndexDirections.actionIndexToChat();
                Navigation.findNavController(mIndexView.tvTitle).navigate(action);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIndexView = null;
    }
}