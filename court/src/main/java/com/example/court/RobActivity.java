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

public class RobActivity extends AppCompatActivity{

    private ImageView profile;
    private EditText editText;
    private Button button;
    private View view_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob);
        profile = findViewById(R.id.issue_profile);
        editText = findViewById(R.id.court_context);
        button = findViewById(R.id.issue_publish);
        view_line = findViewById(R.id.comment_line);
        int user_profile = getIntent().getIntExtra("profile",0);
        profile.setImageResource(user_profile);

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