package com.example.cyclingapp.data.model;

import androidx.room.Room;
import java.util.List;
import com.example.cyclingapp.App;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private Integer userId;
    private String displayName;

    public LoggedInUser(Role role, String email, String firstName, String lastName) {
        AppDatabase db = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        User user = new User();
        user.id = userDao.getAll().blockingGet().size() + 1;
        user.role = role;
        user.email = email;
        user.firstName = firstName;
        user.lastName = lastName;
        userDao.insertAll(user);

        this.userId = user.id;
        this.displayName = user.firstName + " " + user.lastName;

        db.close();
    }

    public String getUserId() {
        return userId.toString();
    }

    public String getDisplayName() {
        return displayName;
    }
}