package com.example.cyclingapp.ui.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cyclingapp.ProfileCreation;
import com.example.cyclingapp.ProfilePage;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.databinding.FragmentEventBinding;

/**
 * A {@link Fragment} that shows options related to events in the cycling app.
 * It allows navigating to event creation or listing based on the user's role.
 */
public class EventFragment extends Fragment {

    private FragmentEventBinding binding; // View binding for the fragment
    private static final int REQUEST_CODE_CREATE_PROFILE = 1; // Unique request code


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment with View Binding
        binding = FragmentEventBinding.inflate(inflater, container, false);

        // Get any arguments passed to the fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            String displayName = bundle.getString("displayName", "User");
            Role role = (Role) bundle.getSerializable("role");
            // Create and show a welcome message
            String welcomeMessage = "Welcome, " + displayName + "!" + "\n" + "You are logged in as \"" + role + "\".";
            Toast.makeText(getActivity(), welcomeMessage, Toast.LENGTH_LONG).show();

            // Enable event creation button only if the user is an admin or club
            if (role != null && (role.equals(Role.ADMIN) || role.equals(Role.CLUB))) {
                binding.btnAdd.setEnabled(true);
                binding.btnAdd.setOnClickListener(v -> navigateToEventCreate());
            }
        }

        // Set up listeners for other UI components
        binding.btnList.setOnClickListener(v -> navigateToEventList());

        binding.viewProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            String displayName = getArguments().getString("displayName", null);
            intent.putExtra("displayName", displayName);
            startActivity(intent);
        });

        binding.createProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileCreation.class);
            String displayName = getArguments().getString("displayName", null);
            intent.putExtra("displayName", displayName);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    /**
     * Navigates to the EventCreate activity.
     */
    private void navigateToEventCreate() {
        Intent intent = new Intent(getActivity(), EventCreate.class);
        startActivity(intent);
    }

    /**
     * Navigates to the EventList activity.
     */
    private void navigateToEventList() {
        Intent intent = new Intent(getActivity(), EventList.class);

        // Pass the role to the EventList activity
        Role role = (Role) getArguments().getSerializable("role");
        String displayName = getArguments().getString("displayName", null);
        intent.putExtra("displayName", displayName);
        intent.putExtra("role", role);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}