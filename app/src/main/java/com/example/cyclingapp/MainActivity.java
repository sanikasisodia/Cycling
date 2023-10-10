package com.example.cyclingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.data.model.User;
import com.example.cyclingapp.data.model.UserDao;

import org.mindrot.jbcrypt.BCrypt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAdminUser();
    }

    void createAdminUser() {
        AppDatabase db = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        User user = new User();
        user.id = java.util.UUID.randomUUID().toString();
        user.role = Role.ADMIN;
        user.email = "admin";
        user.firstName = "ADMIN";
        user.lastName = "USER";
        user.passwordSalt  = BCrypt.gensalt(12);
        user.passwordHash = BCrypt.hashpw("admin", user.passwordSalt);
        userDao.insertAll(user);

        db.close();
    }
}