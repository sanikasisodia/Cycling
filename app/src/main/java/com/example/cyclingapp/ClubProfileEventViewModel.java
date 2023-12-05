package com.example.cyclingapp;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.ClubProfile;
import com.example.cyclingapp.data.model.Event;
import com.example.cyclingapp.data.model.EventDao;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.ui.event.EventAdapter;
import com.example.cyclingapp.ui.event.EventCreate;
import com.example.cyclingapp.ui.event.EventList;

import java.util.ArrayList;
import java.util.List;

public class ClubProfileEventViewModel extends AndroidViewModel {

    private RecyclerView recyclerView; // RecyclerView to display the list of events
    private EventAdapter adapter; // Adapter for the RecyclerView
    private AppDatabase db; // Database instance
    private List<Event> eventList = new ArrayList<>(); // List of events
    private final EventDao eventDao;

    public ClubProfileEventViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDao = db.eventDao();
    }

    public void insertEvent(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insertEvent(event);
        });
    }

    public LiveData<List<Event>> getEventsByUserId(String userId) {
        return eventDao.getEventsByUserId(userId);
    }

}
