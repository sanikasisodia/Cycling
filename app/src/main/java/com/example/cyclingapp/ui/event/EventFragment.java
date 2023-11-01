package com.example.cyclingapp.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.databinding.FragmentEventBinding;

public class EventFragment extends Fragment {

    private FragmentEventBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentEventBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        String displayName = bundle.getString("displayName");
        Role role = (Role) bundle.getSerializable("role");
        binding.welcomeText.setText("Welcome, " + displayName + "!" + "\n" + "You are logged in as \"" + role + "\".");

        // Enable button only if the user is an admin
        if (role.equals(Role.ADMIN)) {
            binding.btnAdd.setEnabled(true);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}