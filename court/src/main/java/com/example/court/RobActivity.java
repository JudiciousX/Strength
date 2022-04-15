package com.example.court;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class RobActivity extends AppCompatActivity{

    private ImageView profile;
    private EditText editText;
    private Button button;
    private View view_line;
    private TextView context_text,username,address,time,name;
    private List<Comment> list = new ArrayList<>();

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
        name = findViewById(R.id.user_name);

        String user_profile = getIntent().getStringExtra("profile");
        String content = getIntent().getStringExtra("content");
        String address_setting = getIntent().getStringExtra("address");
        String time_setting =getIntent().getStringExtra("time");
        String name = getIntent().getStringExtra("name");

        Glide.with(getApplicationContext())
                .load(user_profile)
                .into(profile);
        context_text.setText(content);
        username.setText(name);
        address.setText(address_setting);
        time.setText(time_setting);

        initContext();
        RecyclerView recyclerView = findViewById(R.id.comment_recyclerview);
        CommentAdapter adapter = new CommentAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
        public void initContext() {
        for (int i = 0; i < 20; i++) {
            Comment comment = new Comment();
            comment.setProfile(R.drawable.court_profile);
            comment.setUsername("陈末末");
            comment.setComment("5V5交流赛，欢迎切磋");
            list.add(comment);
        }
    }
}