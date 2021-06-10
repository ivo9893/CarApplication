package com.example.login.Controller;

import android.app.VoiceInteractor;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.login.Callbacks.CheckUserCallback;
import com.example.login.Callbacks.RegisterCallback;
import com.example.login.Globals;
import com.example.login.Model.User;
import com.example.login.RegisterActivity;
import com.example.login.View.IRegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements IRegisterController{

    IRegisterView registerView;

    public RegisterController(IRegisterView registerView){
        this.registerView = registerView;
    }

    @Override
    public void onRegister(User user, Context context) {

        int code = user.isValid();
        if(code == 1){
            registerView.onRegisterFail("Please fill all fields !!");
        }

        JSONObject json = new JSONObject();
        try{
            json.put("username", user.getUsername());
            json.put("password", user.getPassword());
            json.put("email", user.getEmail());
            json.put("phone", user.getPhone());
        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.registerURL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());
                System.out.println(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void checkForExistingUser(User user, Context context, CheckUserCallback callback, RegisterCallback registerCallback) {
        String url = Globals.checkUsernameURL + user.getUsername() + "/" + user.getEmail() + "/" + user.getPhone();

        boolean check = callback.onCheckUserReturn();

        if(check) {
            JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    int username, email, phone;

                    try {
                        username = response.getInt("username");
                        email = response.getInt("email");
                        phone = response.getInt("phone");
                        boolean canRegister = true;
                        if (username != 0) {
                            registerView.existingUser("Username already exists!");
                            canRegister = false;
                        }

                        if (email != 0) {
                            registerView.existingEmail("Email already exists!");
                            canRegister = false;
                        }

                        if (phone != 0) {
                            registerView.existingPhone("Phone number already exists");
                            canRegister = false;
                        }

                        if(canRegister)
                            registerCallback.register();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", error.toString());
                }
            });

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);
        }
    }

    @Override
    public boolean isValidEmail(User user){
        Pattern pattern = Pattern.compile(Globals.emailRegex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches())
            registerView.invalidEmail("Email is invalid!");
        return matcher.matches();
    }

    @Override
    public boolean isValidPassword(User user){
        if( !(user.getPassword().length() >= 6 && user.getPassword().length() <= 10) ){
            registerView.invalidPassword("Password must be between 6 and 10 symbols!");
            return false;
        }

        if(!isStringUpperCase(user.getPassword())) {
            registerView.invalidPassword("Password must contain at least 1 upper case letter!");
            return false;
        }

        return true;
    }

    @Override
    public boolean passwordsMatch(String password1, String password2) {
        if(password1.equals(password2)) {
            return true;
        } else {
            registerView.confirmPasswordDifferent("The passwords don't match!");
            return false;
        }
    }

    @Override
    public boolean isEmptyField(String username, String password, String email, String phone) {
        boolean isEmpty = false;

        if(username.equals("")) {
            isEmpty = true;
            registerView.usernameEmpty("Username is empty!");
        }

        if(password.equals("")){
            isEmpty = true;
            registerView.passwordEmpty("Password is empty");
        }

        if(email.equals("")){
            isEmpty = true;
            registerView.emailEmpty("Email is empty!");
        }

        if(phone.equals("")){
            isEmpty = true;
            registerView.phoneEmpty("Phone is empty!");
        }

        return  isEmpty;
    }

    private static boolean isStringUpperCase(String str){

        //convert String to char array
        char[] charArray = str.toCharArray();

        for(int i=0; i < charArray.length; i++){

            //if any character is not in upper case, return false
            if( Character.isUpperCase( charArray[i] ))
                return true;
        }

        return false;
    }
}
