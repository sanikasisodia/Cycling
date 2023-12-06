package com.example.cyclingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cyclingapp.data.model.ClubProfile;
import com.example.cyclingapp.data.model.ClubProfileDao;
import com.example.cyclingapp.ui.event.EventFragment;

/**
 * Activity for creating a new club profile.
 * This class provides a user interface for entering and saving club profile details.
 */
public class ProfileCreation extends AppCompatActivity {
    private EditText clubNameEditText;
    private EditText socialMediaLinkEditText;
    private EditText phoneNumberEditText;
    private EditText mainContactNameEditText;
    private String displayName;

    private ClubProfileViewModel clubProfileViewModel;


    /**
     * Initializes the activity.
     * Sets up the user interface and view model for profile creation.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this bundle contains the most recent data supplied by onSaveInstanceState.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        clubNameEditText = findViewById(R.id.clubName);
        socialMediaLinkEditText = findViewById(R.id.socialMediaLink);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        mainContactNameEditText = findViewById(R.id.mainContactName);
        displayName = getIntent().getStringExtra("displayName");
        clubProfileViewModel = new ViewModelProvider(this).get(ClubProfileViewModel.class);

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    /**
     * Saves the club profile to the database.
     * Gathers input from text fields and creates a new ClubProfile instance which is then saved.
     * Displays a toast message based on whether the required fields are filled.
     *
     * @return
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Checks if the phone number is 10 digits.
        return phoneNumber.length() == 10;
    }
    private boolean isValidSocialMediaLink(String socialMediaLink) {
        // Checks if it starts with "http" or "https."
        return socialMediaLink.startsWith("http://") || socialMediaLink.startsWith("https://");
    }
    private void saveProfile() {
        String clubName = clubNameEditText.getText().toString();
        String socialMediaLink = socialMediaLinkEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String mainContactName = mainContactNameEditText.getText().toString();


        // Check if phone number is empty
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
        }

        // Check if phone number is valid
        if (!isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
        }

        // Check if social media link is empty
        if (socialMediaLink.isEmpty()) {
            Toast.makeText(this, "Social media link cannot be empty", Toast.LENGTH_SHORT).show();
        }

        // Check if social media link is valid
        if (!isValidSocialMediaLink(socialMediaLink)) {
            Toast.makeText(this, "Invalid social media link format", Toast.LENGTH_SHORT).show();
        }

        else {
            ClubProfile clubProfile = new ClubProfile();
            clubProfile.setClubName(clubName);
            clubProfile.setSocialMediaLink(socialMediaLink);
            clubProfile.setPhoneNumber(phoneNumber);
            clubProfile.setMainContactName(mainContactName);
            clubProfile.setDisplayName(displayName);

            clubProfileViewModel.insertProfile(clubProfile);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("clubName", clubName);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }


}
