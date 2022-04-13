 package com.example.court;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
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
    private ImageView imageView;
    private Context context = getContext();
    private Activity activity = getActivity();
    private int id;

//    public CourtFragment(Context context, Activity activity,int id) {
//        this.id = id;
//        this.context = context;
//        this.activity = activity;
//
//    }

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

//            tabLayout.addTab(tabLayout.newTab().setText("精选"));
//            tabLayout.addTab(tabLayout.newTab().setText("关注"));
//viewPager.setAdapter(new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount()));
//viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
//            viewPager.setCurrentItem(1);


            new TabLayoutMediator(tabLayout, viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText(tabs[position]);
                    Log.d("tttaaa",tabs[position]);
                }
            }).attach();
//        tabLayout.setupWithViewPager();
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
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
        public MyFragmentPagerAdapter( FragmentActivity fragmentActivity, List<Fragment> list) {
            super(fragmentActivity);
            this.list = list;
        }

        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }



//        int num;
//        List<Fragment> list;
//
//        public MyFragmentPagerAdapter(FragmentManager manager, List<Fragment> list) {
//            super(manager);
//            this.list = list;
//
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return createFragment(position);
//        }
//
//        private Fragment createFragment(int pos){
//            Fragment fragment = list.get(pos);
//            if(fragment==null){
//                switch (pos){
//                    case 0:
//                    fragment = new AttentionFragment();
//                    break;
//                    case 1:
//                        fragment = new ChoiceFragment();
//                        break;
//                }
//                list.add(fragment);
//            }
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return num;
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
