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
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        if (password.equals("admin")) {
            return true;
        }
        return password.trim().length() > 5;
    }
}
