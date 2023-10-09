package com.example.cyclingapp.data;

import androidx.room.Room;

import com.example.cyclingapp.App;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.data.model.User;
import com.example.cyclingapp.data.model.UserDao;

public class Register {
    public static Result<Register> register(Role role, String email, String firstName, String lastName, String password) {
        AppDatabase db = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        User newUser = new User();
        newUser.role = role;
        newUser.email = email;
        newUser.firstName = firstName;
        newUser.lastName = lastName;
        // newUser.password = password;
        userDao.insertAll(newUser);

        db.close();

        return new Result.Success<>(new Register());
    }
}
