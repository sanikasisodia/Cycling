package com.example.cyclingapp.ui.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cyclingapp.ClubProfileEventViewModel;
import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.ClubProfile;
import com.example.cyclingapp.data.model.Event;
import com.example.cyclingapp.data.model.LoggedInUser;

/**
 * Activity for creating a new event or editing an existing one in the cycling application.
 */
public class EventCreate extends AppCompatActivity {

    // UI elements
    private EditText editClubName, editName, editEventDetails, editParticipationCount, editFee;
    private Spinner eventType;
    private RadioGroup radioDifficulty;
    private Button btnCreate;

    private ClubProfileEventViewModel clubProfileEventViewModel;

    // Event object for editing
    private Event eventToEdit;

    private String displayName = LoggedInUser.getDisplayName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        // Initialize UI components
        editClubName = findViewById(R.id.editClubName);
        editName = findViewById(R.id.editName);
        eventType = findViewById(R.id.eventType);
        radioDifficulty = findViewById(R.id.radioDifficulty);
        editEventDetails = findViewById(R.id.editEventDetails);
        editParticipationCount = findViewById(R.id.editParticipationCount);
        editFee = findViewById(R.id.editFee);
        btnCreate = findViewById(R.id.btnCreate);

        // Populate the spinner with event types
        populateSpinner();

        // Set the click listener for the create button
        btnCreate.setOnClickListener(v -> createEvent());

        // Check if the intent has an extra for editing an event
        if (getIntent().hasExtra("EDIT_EVENT")) {
            eventToEdit = (Event) getIntent().getSerializableExtra("EDIT_EVENT");
            populateFieldsForEdit(eventToEdit);
            btnCreate.setText("Update");
        }

        btnCreate.setOnClickListener(v -> {
            if (eventToEdit != null) {
                updateEvent();
            } else {
                createEvent();
            }
        });
    }


    /**
     * Populates the UI fields with data from the event being edited.
     * @param eventToEdit The event to edit.
     */

    private void populateFieldsForEdit(Event eventToEdit) {
        // Set the UI fields to the event's data
        editName.setText(eventToEdit.getName());
        editEventDetails.setText(eventToEdit.getDetails());
        eventType.setSelection(((ArrayAdapter<String>) eventType.getAdapter()).getPosition(eventToEdit.getType()));
        radioDifficulty.check(eventToEdit.getDifficulty().equals("Easy") ? R.id.radioEasy :
                eventToEdit.getDifficulty().equals("Medium") ? R.id.radioMedium : R.id.radioHard);
        editParticipationCount.setText(String.valueOf(eventToEdit.getParticipationCount()));
        editFee.setText(String.valueOf(eventToEdit.getFee()));

    }

    /**
     * Updates an existing event with new data from the UI fields.
     */
    private void updateEvent() {
        // Extract data from UI components
        String name = editName.getText().toString();
        String type = eventType.getSelectedItem().toString();
        String details = editEventDetails.getText().toString();
        String clubName = editClubName.getText().toString();
        int difficultyId = radioDifficulty.getCheckedRadioButtonId();
        String difficulty = getDifficultyString(difficultyId);
        String displayName = LoggedInUser.getDisplayName();

        // Parse numerical fields
        int participationCount = 0;
        double fee = 0;
        try {
            participationCount = Integer.parseInt(editParticipationCount.getText().toString());
            fee = Double.parseDouble(editFee.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input for participation count or fee", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate that all fields are filled
        if (clubName.isEmpty()||name.isEmpty() || type.isEmpty() || difficulty.isEmpty() || details.isEmpty() ) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set the updated values to the eventToEdit object
        eventToEdit.setClubName(clubName);
        eventToEdit.setName(name);
        eventToEdit.setType(type);
        eventToEdit.setDifficulty(difficulty);
        eventToEdit.setDetails(details);
        eventToEdit.setParticipationCount(participationCount);
        eventToEdit.setFee(fee);
        eventToEdit.setDisplayName(displayName);


        // Save the updated event
        saveEvent(eventToEdit);
    }


    /**
     * Retrieves a difficulty string based on the selected radio button ID.
     * @param difficultyId The ID of the checked radio button.
     * @return A string representing the selected difficulty.
     */
    private String getDifficultyString(int difficultyId) {
        String difficulty;  // The difficulty string to return


        // Get the selected radio button ID
        if (difficultyId == R.id.radioEasy) {
            difficulty = "Easy";
        } else if (difficultyId == R.id.radioMedium) {
            difficulty = "Medium";
        } else if (difficultyId == R.id.radioHard) {
            difficulty = "Hard";
        } else {
            difficulty = "";
        }
        return difficulty;
    }


    /**
     * Populates the event type spinner with options.
     */
    private void populateSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        String[] eventTypes = {
                "Time Trial", "Hill Climb", "Road Stage Race", "Road Race", "Group Rides"
        };

        // Apply the adapter to the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventType.setAdapter(adapter);
    }

    /**
     * Creates and saves a new event from user inputs.
     * Validates inputs and uses {@code saveEvent} for database insertion.
     * Exits if inputs are invalid or parsing fails.
     */
    private void createEvent() {
        // Extract data from UI components
        String clubName = editClubName.getText().toString();
        String name = editName.getText().toString();
        String type = eventType.getSelectedItem().toString();
        String details = editEventDetails.getText().toString();

        // Get the selected radio button ID
        int difficultyId = radioDifficulty.getCheckedRadioButtonId();
        String difficulty;


        if (difficultyId == R.id.radioEasy) {
            difficulty = "Easy";
        } else if (difficultyId == R.id.radioMedium) {
            difficulty = "Medium";
        } else if (difficultyId == R.id.radioHard) {
            difficulty = "Hard";
        } else {
            difficulty = "";
        }

        // Validate that all fields are filled
        if (clubName.isEmpty()||name.isEmpty() || type.isEmpty() || difficulty.isEmpty() || details.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse numerical fields
        int participationCount = 0;
        double fee = 0;
        try {
            participationCount = Integer.parseInt(editParticipationCount.getText().toString());
            fee = Double.parseDouble(editFee.getText().toString());
        } catch (NumberFormatException e) {
            return;
        }


        // Create a new event object
        Event newEvent = new Event(clubName, name, type, difficulty, details, participationCount, fee, displayName);

        // Save the event to the database
        saveEvent(newEvent);
    }

    /**
     * Persists the event object to the database. It differentiates between creating a new event
     * and updating an existing one based on whether the event has an ID or not.
     *
     * @param event The event object to save to the database.
     */
    private void saveEvent(Event event) {
        // Initialize the database instance
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cycling-db").build();

        new Thread(() -> {
            // Check if the event already has an ID, indicating it's an update
            if (event.getId() > 0) {
                db.eventDao().updateEvent(event);
                // Inform the user of the update on the main thread
                runOnUiThread(() -> Toast.makeText(EventCreate.this, "Event updated", Toast.LENGTH_SHORT).show());
            } else {
                // If there's no ID, it's a new event, insert it into the database
                db.eventDao().insertEvent(event);
                // Inform the user of the creation on the main thread
                runOnUiThread(() -> Toast.makeText(EventCreate.this, "Event created", Toast.LENGTH_SHORT).show());
            }
            finish();
        }).start();
    }


}
