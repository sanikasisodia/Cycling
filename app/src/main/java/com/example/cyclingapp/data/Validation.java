package com.example.cyclingapp.data;

import android.util.Patterns;

public class Validation {
    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.equals("admin")) {
            return true;
        }
        if(email.equals("gccadmin")){
            return true;
        }

        if (email.equals("cyclingaddict")){
            return true;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        if (password.equals("admin")) {
            return true;
        }
        if (password.equals("GCCRocks!")){
            return true;
        }

        if (password.equals("cyclingIsLife!")){
            return true;
        }
        return password.trim().length() > 5;
    }
}
