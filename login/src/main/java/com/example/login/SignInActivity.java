package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import Fragments.ForgetFragment;
import Fragments.SignInFragment;
import IView.ISignView;

public class SignInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //获取需要跳转到哪个页面
        Intent intent = getIntent();
        String data = intent.getStringExtra("type");
        if(data.equals("forget")) {
            replaceFragment(new ForgetFragment(this, this));
        }else {
            replaceFragment(new SignInFragment(this, this));
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sign_frame, fragment);
        fragmentTransaction.commit();
    }


}