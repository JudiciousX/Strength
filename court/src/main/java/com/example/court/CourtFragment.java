package com.example.court;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.court.attention.AttentionFragment;
import com.example.court.choice.ChoiceFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CourtFragment extends Fragment{
    private String[] tabs = {"精选", "关注"};


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                        ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.court_fragment, container, false);
        TabLayout tabLayout = view.findViewById(R.id.court_toolbar_tablayout);
        ViewPager2 viewPager = view.findViewById(R.id.court_toolbar_viewpager);
        Button button = view.findViewById(R.id.court_toolbar_add);

        List<Fragment> list  = new ArrayList<>();
        list.add(new ChoiceFragment());
        list.add(new AttentionFragment());


        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        new TabLayoutMediator(tabLayout, viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
                Log.d("tttaaa",tabs[position]);
            }
        }).attach();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IssueActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    static class MyFragmentPagerAdapter extends FragmentStateAdapter {

        List<Fragment> list;

        public MyFragmentPagerAdapter(FragmentActivity activity, List<Fragment> list) {
            super(activity);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
