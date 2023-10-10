package com.example.cyclingapp.data;

import androidx.room.Room;

import com.example.cyclingapp.App;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.data.model.User;
import com.example.cyclingapp.data.model.UserDao;

import org.mindrot.jbcrypt.BCrypt;

public class Setup {
    public Setup() {
        AppDatabase db = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        User user = new User();
        user.id = java.util.UUID.randomUUID().toString();
        user.role = Role.ADMIN;
        user.email = "admin";
        user.firstName = "ADMIN";
        user.lastName = "USER";
        user.passwordSalt = BCrypt.gensalt(12);
        user.passwordHash = BCrypt.hashpw("admin", user.passwordSalt);
        userDao.insertAll(user);

        db.close();
    }
}
