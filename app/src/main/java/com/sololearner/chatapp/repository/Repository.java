package com.sololearner.chatapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.sololearner.chatapp.core.User;

public class Repository {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> isLogin = new MutableLiveData<>();

    public Repository() {
        isCurrentlyLogin();
    }

    public void createAccount(User user) {
        mAuth.createUserWithEmailAndPassword(user.getUserName(), user.getPassword())
                .addOnCompleteListener(task -> isSuccess.setValue(task.isSuccessful()));
    }

    public void loginAccount(User user) {
        mAuth.signInWithEmailAndPassword(user.getUserName(), user.getPassword())
                .addOnCompleteListener(task -> isSuccess.setValue(task.isSuccessful()));
    }

    private void isCurrentlyLogin() {
        isLogin.setValue(mAuth.getCurrentUser() != null);
    }

}
