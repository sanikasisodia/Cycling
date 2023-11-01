package com.example.cyclingapp.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Event;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventCreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText editName, editEventDetails, editParticipationCount, editFee;
    private Spinner eventType;
    private RadioGroup radioDifficulty;
    private Button btnCreate;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public EventCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    private String mParam1;
    private String mParam2;

    public static EventCreateFragment newInstance(String param1, String param2) {
        EventCreateFragment fragment = new EventCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editName = view.findViewById(R.id.editName);
        eventType = view.findViewById(R.id.eventType);
        radioDifficulty = view.findViewById(R.id.radioDifficulty);
        editEventDetails = view.findViewById(R.id.editEventDetails);
        editParticipationCount = view.findViewById(R.id.editParticipationCount);
        editFee = view.findViewById(R.id.editFee);
        btnCreate = view.findViewById(R.id.btnCreate);

        populateSpinner();

        btnCreate.setOnClickListener(v -> createEvent());
    }

    private void populateSpinner() {
        String[] eventTypes = {
                "Time Trial", "Hill Climb", "Road Stage Race", "Road Race", "Group Rides"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, eventTypes);
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
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "cycling-db").build();

        new Thread(() -> {
            db.eventDao().insertEvent(event);
        }).start();

        // Confirmation msg??
    }
}