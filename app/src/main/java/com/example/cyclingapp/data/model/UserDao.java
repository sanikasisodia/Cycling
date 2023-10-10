package com.example.cyclingapp.data.model;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertUsers(List<User> users);

    @Query("SELECT * FROM user")
    public Single<List<User>> getAll();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
