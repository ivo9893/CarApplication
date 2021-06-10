package com.example.login.Controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.login.Globals;
import com.example.login.Model.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginController implements ILoginController{


    @Override
    public void onLogin(User user, Context context) {
        String url = Globals.loginURL + user.getUsername() + "/" + user.getPassword();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
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
