package com.example.cyclingapp.data;

import androidx.room.Room;

import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.LoggedInUser;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.data.model.User;
import com.example.cyclingapp.data.model.UserDao;

import java.io.IOException;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String email, String password) {
        try {
            LoggedInUser user =
                    new LoggedInUser(
                            email,
                            password);
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}