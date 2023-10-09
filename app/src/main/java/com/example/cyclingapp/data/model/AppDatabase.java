package com.example.cyclingapp.data.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private Object getApplicationContext() {
        return null;
    }

    public abstract UserDao userDao();
}
