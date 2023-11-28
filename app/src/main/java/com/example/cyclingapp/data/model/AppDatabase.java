package com.example.cyclingapp.data.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract database class that extends RoomDatabase.
 * This class serves as the main access point to the persisted data using the Room persistence library.
 * It lists all the entities that the database includes and the version of the database for schema management.
 * The database class is defined to be abstract and extends RoomDatabase to benefit from Room's database management capabilities.
 */
@Database(entities = {User.class, Event.class, ClubProfile.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // Sets the number of threads for the database operations
    private static final int NUMBER_OF_THREADS = 4;

    // Executor service to handle database operations asynchronously
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton instance of the AppDatabase
    private static volatile AppDatabase INSTANCE;

    /**
     * Gets the singleton instance of the database.
     * Uses a synchronized block to ensure that only one instance of the database is created.
     *
     * @param context The application context.
     * @return The singleton instance of AppDatabase.
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "your_database_name")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
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

    /**
     * Defines the method to get the DAO for club profile entities.
     * Room will generate the implementation for this method.
     *
     * @return ClubProfileDao interface for accessing club profile-related database operations.
     */
    public abstract ClubProfileDao clubProfileDao();

}
