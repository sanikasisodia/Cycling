package com.example.cyclingapp.data.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Abstract database class that extends RoomDatabase.
 * This class serves as the main access point to the persisted data using the Room persistence library.
 * It lists all the entities that the database includes and the version of the database for schema management.
 * The database class is defined to be abstract and extends RoomDatabase to benefit from Room's database management capabilities.
 */
@Database(entities = {User.class, Event.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Defines the method to get the DAO for user entities.
     * Room will generate the implementation for this method.
     *
     * @return UserDao interface for accessing user-related database operations.
     */
    public abstract UserDao userDao();

    /**
     * Defines the method to get the DAO for event entities.
     * Room will generate the implementation for this method.
     *
     * @return EventDao interface for accessing event-related database operations.
     */
    public abstract EventDao eventDao();
}
