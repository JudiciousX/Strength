package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.Blogs_Adapter;

public class Recycler_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private int id;
    private RecyclerView.Adapter adapter;
    private List<View> list = new ArrayList<>();

    public Recycler_Fragment(Context context, int id, RecyclerView.Adapter adapter) {
        this.context = context;
        this.id = id;
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_personal, container, false);
        recyclerView = view.findViewById(R.id.personal_RV2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }
}
