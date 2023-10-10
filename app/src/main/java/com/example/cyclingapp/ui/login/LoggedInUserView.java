package com.example.cyclingapp.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String role;

    LoggedInUserView(String displayName, String role) {
        this.displayName = displayName;
        this.role = role;
    }

    String getDisplayName() {
        return displayName;
    }

    public String getRole() {
        return role;
    }
}