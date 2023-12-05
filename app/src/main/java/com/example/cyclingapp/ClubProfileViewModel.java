package com.example.cyclingapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.ClubProfile;
import com.example.cyclingapp.data.model.ClubProfileDao;

/**
 * ViewModel for the club profile.
 * Provides a layer of abstraction between the UI and the database.
 * Handles the communication of the application with the database to perform operations related to club profiles.
 */
public class ClubProfileViewModel extends AndroidViewModel {
    private final ClubProfileDao clubProfileDao; // DAO for club profiles

    /**
     * Constructor for ClubProfileViewModel.
     *
     * @param application The application that this ViewModel is scoped to.
     */
    public ClubProfileViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        clubProfileDao = db.clubProfileDao();
    }

    /**
     * Inserts a club profile into the database.
     * The insertion is performed asynchronously.
     *
     * @param clubProfile The club profile to insert into the database.
     */
    public void insertProfile(ClubProfile clubProfile) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            clubProfileDao.insertProfile(clubProfile);
        });
    }

    /**
     * Retrieves a club profile from the database based on the display name.
     * This method returns a LiveData object containing the club profile.
     *
     * @param displayName The display name of the club profile to retrieve.
     * @return LiveData containing the ClubProfile object.
     */
    public LiveData<ClubProfile> getProfileByDisplayName(String displayName) {
        return clubProfileDao.getProfileByDisplayName(displayName);
    }
}