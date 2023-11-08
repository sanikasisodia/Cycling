package com.example.cyclingapp.ui.event;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Event;

import java.util.ArrayList;
import java.util.List;


public class EventList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView = findViewById(R.id.rvEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();
    }

    private void loadEvents() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cycling-db").build();

        new Thread(() -> {
            eventList = db.eventDao().getAllEvents();
            adapter = new EventAdapter(eventList);
            runOnUiThread(() -> recyclerView.setAdapter(adapter));
        }).start();
    }
}

