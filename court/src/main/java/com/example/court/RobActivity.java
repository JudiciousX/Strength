package com.example.court;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class RobActivity extends AppCompatActivity {

    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob);
        profile = findViewById(R.id.issue_profile);
        int user_profile = getIntent().getIntExtra("profile",0);
        profile.setImageResource(user_profile);
    }
}