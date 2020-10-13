package com.sololearner.chatapp.core;

public class User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isPasswordValid(){
        return password.length() >= 8 && password.length() <= 16;
    }
    public Boolean isUserNameValid(){
        return userName.length() >= 8 && userName.length() <= 16;
    }
}
