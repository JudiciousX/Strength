package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commlib.RetrofitBase;
import com.example.personal.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.Blogs_Adapter;
import IRequest.NameRequest;
import Tool.Data;
import Tool.Requests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Recycler_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private int id;
    private int position;
    private RecyclerView.Adapter adapter;
    private List<View> list = new ArrayList<>();

    public Recycler_Fragment(Context context, int id, RecyclerView.Adapter adapter, int position) {
        this.position = position;
        this.context = context;
        this.id = id;
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_personal, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.personal_RV2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        if(position == 0) {

            recyclerView.setAdapter(new Blogs_Adapter(getContext()));
            Retrofit retrofit = new RetrofitBase().getRetrofit();
            NameRequest nameRequest = retrofit.create(NameRequest.class);
            nameRequest.getArticleList(RetrofitBase.mobileToken, RetrofitBase.uid).enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {

                    Personal_Fragment.ArticleContentList = response.body().getData();
                    Log.d("scout", response.body().getData().get(0).toString() + "");
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    Log.d("scout", "请求失败");
                }
            });
            nameRequest.getArticleList1(RetrofitBase.mobileToken, RetrofitBase.uid).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Log.d("scout", "onResponse: " + response.body().toString());
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        }
    }
}
