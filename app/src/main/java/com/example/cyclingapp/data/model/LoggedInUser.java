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

    public LoggedInUser(String email, String password) {
        AppDatabase db = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        User test = new User();
        test.email = "test@gmail.com";
        test.firstName = "Admin";
        test.lastName = "User";
        test.role = Role.ADMIN;
        userDao.insertAll(test);

        List<User> users = userDao.getAll().blockingGet();
        for (User user : users) {
            if (user.email.equals(email) && /* check password */ true) {
                userId = user.id;
                displayName = user.firstName + " " + user.lastName;
            }
        }

        db.close();
    }

    public String getUserId() {
        return userId.toString();
    }

    public String getDisplayName() {
        return displayName;
    }
}