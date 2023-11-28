package com.example.cyclingapp.data.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClubProfileDao {
    @Query("SELECT * FROM club_profiles WHERE id = :id")
    LiveData<ClubProfile> getProfileById(int id);


    @Query("SELECT * FROM club_profiles WHERE user_id = :userId")
    LiveData<ClubProfile> getProfileByUserId(String userId);

    @Query("SELECT * FROM club_profiles WHERE display_name = :displayName")
    LiveData<ClubProfile> getProfileByDisplayName(String displayName);

    @Insert
    void insertProfile(ClubProfile clubProfile);

    @Update
    void updateProfile(ClubProfile clubProfile);

}
