package com.example.court.attention;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.court.CourtAdapter;
import com.example.court.Court_Context;
import com.example.court.R;

import java.util.ArrayList;

public class AttentionFragment extends Fragment {
    private View view;

    private ArrayList<Court_Context> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.court_recyclerview,container,false);
        initContext();
        initRecyclerView();
        return view;
    }
    public void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.dynamic_recycler);
        CourtAdapter adapter = new CourtAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    public void initContext(){
        for(int i = 0 ;i<20;i++){
            Court_Context court_context = new Court_Context();
            court_context.setProfile(R.drawable.court_profile);
            court_context.setName("陈末末");
            court_context.setAddress("西安抛物线篮球场");
            court_context.setTime("1月18日 19:00");
            court_context.setInformation("5V5交流赛，欢迎切磋");
            list.add(court_context);
        }
    }
}
