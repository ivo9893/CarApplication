package com.example.login.View;

public interface IRegisterView {

    void onRegisterSuccess(String message);
    void onRegisterFail(String message);
    void invalidEmail(String message);
    void invalidPassword(String message);
    void existingUser(String message);
    void usernameEmpty(String message);
    void passwordEmpty(String message);
    void confirmPasswordDifferent(String message);
    void phoneEmpty(String message);
    void emailEmpty(String message);
    void existingEmail(String message);
    void existingPhone(String message);
}
