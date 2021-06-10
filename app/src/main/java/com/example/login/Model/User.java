package com.example.login.Model;

import android.text.TextUtils;

import com.example.login.Globals;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername(){
        return username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int isValid(){
        if(TextUtils.isEmpty(getEmail()) || TextUtils.isEmpty(getUsername()) || TextUtils.isEmpty(getPassword()) || TextUtils.isEmpty(getPhone()))
            return 1;

        return 0;
    }


}
