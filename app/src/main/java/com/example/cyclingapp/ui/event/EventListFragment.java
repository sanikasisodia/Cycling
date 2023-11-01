package com.example.cyclingapp.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.AppDatabase;
import com.example.cyclingapp.data.model.Event;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private AppDatabase appDatabase;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFragment.
     */
    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
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
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "app-database").allowMainThreadQueries().build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout eventListLayout = view.findViewById(R.id.eventListLayout);
        List<Event> events = appDatabase.eventDao().getAllEvents();

        for (Event event : events) {
            View eventItem = inflater.inflate(R.layout.event_list_item, null);

            TextView nameView = eventItem.findViewById(R.id.nameView);
            nameView.setText(event.getName());

            Button btnEdit = eventItem.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(v -> {
                // Need to implement this UI wise? Edit what??
            });

            Button btnRemove = eventItem.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(v -> {
                appDatabase.eventDao().deleteEvent(event);
                eventListLayout.removeView(eventItem);
                // maybe confirmation??
                // ask if they are sure they want to delete the event


            });

            eventListLayout.addView(eventItem);
        }
    }


}