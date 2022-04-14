package com.example.court;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RobActivity extends AppCompatActivity{

    private ImageView profile;
    private EditText editText;
    private Button button;
    private View view_line;
    private TextView context_text,username,address,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob);
        profile = findViewById(R.id.issue_profile);
        editText = findViewById(R.id.court_context);
        button = findViewById(R.id.issue_publish);
        view_line = findViewById(R.id.comment_line);
        context_text = findViewById(R.id.court_context_text);
        username = findViewById(R.id.user_name);
        address = findViewById(R.id.court_local_text);
        time = findViewById(R.id.court_time_text);

        String user_profile = getIntent().getStringExtra("profile");
        String content = getIntent().getStringExtra("content");
//        String time =getIntent().get

        Glide.with(getApplicationContext())
                .load(user_profile)
                .into(profile);
        context_text.setText(content);

        editText.addTextChangedListener(new TextWatcher() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(editText.getText().toString().trim())) {
//                    button.setBackgroundColor(Color.GRAY);
                    button.setVisibility(View.GONE);
                    button.setEnabled(false);
                } else {
                    //设置selector来控制Button背景颜色
                   button.setVisibility(View.VISIBLE);
                    button.setBackgroundColor(Color.rgb(235,88,41));
                    button.setEnabled(true);
                }
            }
            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
//
            }
        });
    }
}