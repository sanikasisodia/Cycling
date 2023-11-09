package com.example.cyclingapp.ui.event;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Event;
import com.example.cyclingapp.data.model.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display a list of cycling events.
 */
public class EventList extends AppCompatActivity implements EventAdapter.EventAdapterListener {

    private RecyclerView recyclerView; // RecyclerView to display the list of events
    private EventAdapter adapter; // Adapter for the RecyclerView
    private AppDatabase db; // Database instance

    private List<Event> eventList = new ArrayList<>(); // List of events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        // Initialize the database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cycling-db").build();

        Role userRole = (Role) getIntent().getSerializableExtra("role");

        // Setup RecyclerView
        recyclerView = findViewById(R.id.rvEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(eventList, this, userRole );
        recyclerView.setAdapter(adapter);

        // Load events from the database
        loadEvents();
    }




    @Override
    protected void onResume() {
        super.onResume();
        // Reload the events when resuming the activity
        loadEvents();
    }

    /**
     * Loads events from the database and updates the adapter.
     */
    private void loadEvents() {
        new Thread(() -> {
            List<Event> events = db.eventDao().getAllEvents();
            runOnUiThread(() -> adapter.setEvents(events));
        }).start();
    }

    /**
     * Handles the edit button click for an event.
     *
     * @param event The event to edit.
     */
    @Override
    public void onEditClick(Event event) {
        Intent intent = new Intent(EventList.this, EventCreate.class);
        intent.putExtra("EDIT_EVENT", event);
        startActivity(intent);
    }

    /**
     * Handles the delete button click for an event.
     *
     * @param event The event to delete.
     */
    @Override
    public void onDeleteClick(Event event) {
        new Thread(() -> {
            db.eventDao().deleteEvent(event);
            loadEvents();
        }).start();
    }
}
