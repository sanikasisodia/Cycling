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

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Event;

public class EventCreate extends AppCompatActivity {

    private EditText editName, editEventDetails, editParticipationCount, editFee;
    private Spinner eventType;
    private RadioGroup radioDifficulty;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        editName = findViewById(R.id.editName);
        eventType = findViewById(R.id.eventType);
        radioDifficulty = findViewById(R.id.radioDifficulty);
        editEventDetails = findViewById(R.id.editEventDetails);
        editParticipationCount = findViewById(R.id.editParticipationCount);
        editFee = findViewById(R.id.editFee);
        btnCreate = findViewById(R.id.btnCreate);

        populateSpinner();

        btnCreate.setOnClickListener(v -> createEvent());
    }

    private void populateSpinner() {
        String[] eventTypes = {
                "Time Trial", "Hill Climb", "Road Stage Race", "Road Race", "Group Rides"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventType.setAdapter(adapter);
    }

    private void createEvent() {
        String name = editName.getText().toString();
        String type = eventType.getSelectedItem().toString();
        String details = editEventDetails.getText().toString();

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


        int participationCount;
        double fee;
        try {
            participationCount = Integer.parseInt(editParticipationCount.getText().toString());
            fee = Double.parseDouble(editFee.getText().toString());
        } catch (NumberFormatException e) {
            return;
        }

        if (name.isEmpty() || type.isEmpty() || difficulty.isEmpty() || details.isEmpty()) {
            return;
        }

        Event newEvent = new Event(name, type, difficulty, details, participationCount, fee);
        saveEvent(newEvent);
    }

    private void saveEvent(Event event) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cycling-db").build();

        new Thread(() -> {
            db.eventDao().insertEvent(event);
            runOnUiThread(() -> {
                Toast.makeText(EventCreate.this, "Event saved", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

}
