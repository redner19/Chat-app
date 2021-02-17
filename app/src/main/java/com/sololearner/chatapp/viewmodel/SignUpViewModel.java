package com.sololearner.chatapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sololearner.chatapp.core.User;
import com.sololearner.chatapp.repository.Repository;

public class SignUpViewModel extends ViewModel {
    private final Repository repository;
    public LiveData<Boolean> isSuccess;
    private final MutableLiveData<Boolean> isEmailValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPassValid = new MutableLiveData<>();

    public SignUpViewModel() {
        repository = new Repository();
        isSuccess = repository.isSuccess;
    }

    public Boolean validateInputUser(User user){
        isEmailValid.setValue(user.isUserNameValid());
        isPassValid.setValue(user.isPasswordValid());
        return user.isUserAndPasswordValid();
    }

    public LiveData<Boolean> isEmailValid() {
        return isEmailValid;
    }

    public LiveData<Boolean> isPasswordValid() {
        return isPassValid;
    }

    public void createAccount(User user) {
        repository.createAccount(user);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
