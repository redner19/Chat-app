package com.sololearner.chatapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sololearner.chatapp.core.User;
import com.sololearner.chatapp.repository.Repository;

public class LoginViewModel extends ViewModel {
    private final Repository repository;
    public LiveData<Boolean> isSuccess;



    public LoginViewModel() {
        repository = new Repository();
        isSuccess = repository.isSuccess;
    }

    public void loginAccount(User user) {
        repository.loginAccount(user);
    }
}
