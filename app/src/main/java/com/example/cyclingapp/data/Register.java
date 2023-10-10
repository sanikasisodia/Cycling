package com.example.cyclingapp.data;

import androidx.room.Room;

import com.example.cyclingapp.App;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.data.model.User;
import com.example.cyclingapp.data.model.UserDao;

import org.mindrot.jbcrypt.BCrypt;

public class Register {

    User user = new User();

    public Result<Register> register(Role role, String email, String firstName, String lastName, String password) {
        AppDatabase db = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        if (Validation.isEmailValid(email) == false) {
            return new Result.Error(new Exception("Invalid email"));
        }

        if (Validation.isPasswordValid(password) == false) {
            return new Result.Error(new Exception("Password must be at least 5 characters"));
        }

        user.id = java.util.UUID.randomUUID().toString();
        user.role = role;
        user.email = email;
        user.firstName = firstName;
        user.lastName = lastName;
        user.passwordSalt = BCrypt.gensalt(12);
        user.passwordHash = BCrypt.hashpw(password, user.passwordSalt);
        userDao.insertAll(user);

        System.out.println("User: " + user.toString());

        db.close();

        return new Result.Success<>(this);
    }

    public String getDisplayName() {
        return user.firstName + " " + user.lastName;
    }
}
