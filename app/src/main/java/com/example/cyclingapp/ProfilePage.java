package com.example.cyclingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.example.cyclingapp.data.model.ClubProfile;
import com.example.cyclingapp.data.model.Event;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.ui.event.EventAdapter;
import com.example.cyclingapp.ui.event.EventViewModel;

import java.util.ArrayList;


/**
 * Activity for displaying and updating a club's profile.
 * Provides functionality to view and edit club profile details, including selecting a club logo.
 */
public class ProfilePage extends AppCompatActivity {

    private TextView clubNameTextView; // TextView for displaying the club name
    private TextView socialMediaLinkTextView; // TextView for displaying the social media link
    private TextView phoneNumberTextView; // TextView for displaying the phone number
    private TextView mainContactNameTextView; // TextView for displaying the main contact name
    private ImageView clubLogoImageView; // ImageView for displaying the club logo

    private EventViewModel eventViewModel;
    private EventAdapter eventAdapter;

    private RecyclerView clubEventsRecyclerView;
    private ClubProfileViewModel clubProfileViewModel; // ViewModel for the club profile

    private int[] imageIds = new int[]{R.drawable.logo1, R.drawable.logo2, R.drawable.logo3};

    /**
     * Initializes the activity.
     * Sets up the user interface, view model, and initializes the process to load club profile data.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this bundle contains the most recent data supplied by onSaveInstanceState.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Initialize UI components
        clubNameTextView = findViewById(R.id.clubName);
        socialMediaLinkTextView = findViewById(R.id.socialMediaLink);
        phoneNumberTextView = findViewById(R.id.phoneNumber);
        mainContactNameTextView = findViewById(R.id.mainContactName);
        clubLogoImageView = findViewById(R.id.clubLogo);
        clubEventsRecyclerView = findViewById(R.id.clubEvents);

        clubLogoImageView.setOnClickListener(view -> showLogoSelectionDialog());

        // Initialize RecyclerView for events
        eventAdapter = new EventAdapter(new ArrayList<>(), EventAdapter.EventAdapterListener, Role.CLUB);
        clubEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        clubEventsRecyclerView.setAdapter(eventAdapter);

        // Get ViewModel
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        clubProfileViewModel = new ViewModelProvider(this).get(ClubProfileViewModel.class);

        String displayName = getIntent().getStringExtra("displayName");
        String userId = getUserId();
        eventViewModel.getEventsByUserId(userId).observe(this, events -> {
            eventAdapter.setEvents(events);
        });
        if (displayName != null) {
            clubProfileViewModel.getProfileByDisplayName(displayName).observe(this, clubProfile -> {
                if (clubProfile != null) {
                    updateUI(clubProfile);
                }
            });
        }
    }

    private String getUserId() {
        // Retrieving the user ID passed through an Intent
        String userId = getIntent().getStringExtra("USER_ID");
        if (userId != null && !userId.isEmpty()) {
            return userId;
        }
        return null;
    }

    private String getClubId() {
        // Example of retrieving the club ID passed through an Intent
        return getIntent().getStringExtra("CLUB_ID");
    }



    /**
     * Updates the UI elements with the provided club profile data.
     *
     * @param clubProfile The club profile to display.
     */
    private void updateUI(ClubProfile clubProfile) {
        clubNameTextView.setText(clubProfile.getClubName());
        socialMediaLinkTextView.setText(clubProfile.getSocialMediaLink());
        phoneNumberTextView.setText(clubProfile.getPhoneNumber());
        mainContactNameTextView.setText(clubProfile.getMainContactName());

        //update UI with events
        eventViewModel.getEventsByUserId(clubProfile.getUserId()).observe(this, events -> {
            if (events != null) {
                // Update RecyclerView adapter with the list of events
                eventAdapter.setEvents(events);
            } else {

            }
        });
    }
    /**
     * Displays a dialog allowing the user to select a logo from predefined drawable resources.
     * Updates the club logo ImageView with the selected image.
     */
    private void showLogoSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_image_picker, null);
        GridView gridView = dialogView.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this, imageIds));

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            clubLogoImageView.setImageResource(imageIds[position]);
            dialog.dismiss();
        });

        dialog.show();
    }

}
