package com.example.login.Controller;

import android.content.Context;

import com.example.login.Callbacks.CheckUserCallback;
import com.example.login.Callbacks.RegisterCallback;
import com.example.login.Model.User;

public interface IRegisterController {

    void onRegister(User user, Context context);
    void checkForExistingUser(User user, Context context, CheckUserCallback callback, RegisterCallback registerCallback);
    boolean isValidEmail(User user);
    boolean isValidPassword(User user);
    boolean isEmptyField(String username, String password, String email, String phone);
    boolean passwordsMatch(String password1, String password2);
}
