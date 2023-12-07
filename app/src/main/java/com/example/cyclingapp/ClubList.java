package com.example.cyclingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ClubList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClubProfileViewModel clubProfileViewModel;
    private ClubListAdapter adapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        recyclerView = findViewById(R.id.recycler_view_clubs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClubListAdapter();
        recyclerView.setAdapter(adapter);

        searchEditText = findViewById(R.id.search_club);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        clubProfileViewModel = new ViewModelProvider(this).get(ClubProfileViewModel.class);
        clubProfileViewModel.getAllClubProfiles().observe(this, profiles -> {
            adapter.submitList(profiles);
        });

        adapter.setOnItemClickListener(clubProfile -> {
            Intent intent = new Intent(ClubList.this, ProfilePage.class);
            intent.putExtra("CLUB_PROFILE_ID", clubProfile.getId());
            startActivity(intent);
        });
    }
}
