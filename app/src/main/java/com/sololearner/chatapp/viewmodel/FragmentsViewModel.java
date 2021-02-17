package com.sololearner.chatapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sololearner.chatapp.repository.Repository;

public class FragmentsViewModel extends ViewModel {
    public LiveData<Boolean> isLogin;


    public FragmentsViewModel() {
        Repository repository = new Repository();
        isLogin = repository.isLogin;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
