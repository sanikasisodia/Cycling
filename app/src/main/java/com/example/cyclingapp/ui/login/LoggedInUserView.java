package com.example.cyclingapp.ui.login;

import com.example.cyclingapp.data.model.Role;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private Role role;

    LoggedInUserView(String displayName, Role role) {
        this.displayName = displayName;
        this.role = role;
    }

    String getDisplayName() {
        return displayName;
    }

    public Role getRole() {
        return role;
    }
}