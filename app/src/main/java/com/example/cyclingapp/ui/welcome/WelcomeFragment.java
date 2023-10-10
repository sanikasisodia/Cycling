package com.example.cyclingapp.ui.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cyclingapp.databinding.FragmentRegisterBinding;
import com.example.cyclingapp.databinding.FragmentWelcomeBinding;

public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentWelcomeBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        String displayName = bundle.getString("displayName");
        String role = bundle.getString("role");
        binding.welcomeText.setText("Welcome, " + displayName + "!" + "\n" + "You are logged in as \"" + role + "\".");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}