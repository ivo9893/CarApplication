package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.Callbacks.CheckUserCallback;
import com.example.login.Callbacks.RegisterCallback;
import com.example.login.Controller.IRegisterController;
import com.example.login.Controller.RegisterController;
import com.example.login.Model.User;
import com.example.login.View.IRegisterView;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements IRegisterView {

    IRegisterController registerController;
    Button register;
    Context context;
    TextInputEditText username, password, confirmPassword, email, phone;


    @Override
    public void onRegisterSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterFail(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void invalidEmail(String message) {
        email.setError(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        registerController = new RegisterController(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearErrors();
                User user = new User(username.getText().toString(), password.getText().toString(), email.getText().toString(), phone.getText().toString());

                registerController.checkForExistingUser(user, context, new CheckUserCallback() {
                    @Override
                    public boolean onCheckUserReturn() {
                        boolean canRegister = true;

                        if (!registerController.isValidEmail(user)) {
                            canRegister = false;
                        }

                        if (!registerController.isValidPassword(user)) {
                            canRegister = false;
                        }

                        if (!registerController.passwordsMatch(user.getPassword(), confirmPassword.getText().toString())) {
                            canRegister = false;
                        }


                        if (registerController.isEmptyField(username.getText().toString(), password.getText().toString(), email.getText().toString(), phone.getText().toString())) {
                            canRegister = false;
                        }

                        return canRegister;
                    }
                }, new RegisterCallback() {
                    @Override
                    public void register() {
                        registerController.onRegister(user, context);
                        onBackPressed();
                    }
                });
            }
        });


    }

    public void clearErrors(){
        username.setError(null);
        password.setError(null);
        confirmPassword.setError(null);
        email.setError(null);
        phone.setError(null);
    }


    @Override
    public void invalidPassword(String message) {
        password.setError(message);
    }

    @Override
    public void existingUser(String message) {
        username.setError(message);
    }

    @Override
    public void usernameEmpty(String message) {
        username.setError(message);
    }

    @Override
    public void passwordEmpty(String message) {
        password.setError(message);
    }

    @Override
    public void phoneEmpty(String message) {
        phone.setError(message);
    }

    @Override
    public void emailEmpty(String message) {
        email.setError(message);
    }

    @Override
    public void confirmPasswordDifferent(String message) {
        confirmPassword.setError(message);
    }

    @Override
    public void existingEmail(String message) {
        email.setError(message);
    }

    @Override
    public void existingPhone(String message) {
        phone.setError(message);
    }
}