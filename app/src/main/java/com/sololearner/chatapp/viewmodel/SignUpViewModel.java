package com.sololearner.chatapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sololearner.chatapp.core.User;
import com.sololearner.chatapp.repository.Repository;

public class SignUpViewModel extends ViewModel {
    private final Repository repository;
    public LiveData<Boolean> isSuccess;


    public SignUpViewModel() {
        repository = new Repository();
        isSuccess = repository.isSuccess;
    }

    public void createAccount(User user) {
        repository.createAccount(user);
    }

}
