package com.example.court;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.court.attention.AttentionFragment;
import com.example.court.choice.ChoiceFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CourtActivity extends AppCompatActivity {

    private String[] tabs = {"精选", "关注"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court);
        ActionBar actionBar = getSupportActionBar();//隐藏标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        TabLayout tabLayout = findViewById(R.id.message_toolbar_tablayout);
        ViewPager2 viewPager = findViewById(R.id.message_toolbar_viewpager);


        List<Fragment> list  = new ArrayList<>();
        list.add(new ChoiceFragment());
        list.add(new AttentionFragment());


        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(CourtActivity.this, list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        new TabLayoutMediator(tabLayout, viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
                Log.d("tttaaa",tabs[position]);
            }
        }).attach();
    }

    static class MyFragmentPagerAdapter extends FragmentStateAdapter {

        List<Fragment> list;

        public MyFragmentPagerAdapter(FragmentActivity fa, List<Fragment> list) {
            super(fa);
            this.list = list;

        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
}