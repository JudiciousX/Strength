package com.example.court.attention;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.commlib.RetrofitBase;
import com.example.court.Api;
import com.example.court.Article;
import com.example.court.CourtAdapter;
import com.example.court.Court_Context;
import com.example.court.Data;
import com.example.court.R;
import com.example.court.RobActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.alibaba.android.arouter.compiler.utils.Consts.TAG;

public class AttentionFragment extends Fragment {
    private View view;
    private Retrofit mRetrofit;
    private TextView textView;
    private int id;
    private Context context;
    private Activity activity;
    private SwipeRefreshLayout swipeRefreshLayout;


    private List<Court_Context> list = new ArrayList<>();

//    public ChoiceFragment(Context context, Activity activity) {
//        this.context = context;
//        this.activity = activity;
//
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.court_recyclerview, container, false);



//        requestData();
        Court_Context court_context = new Court_Context();
        court_context.setProfile(R.drawable.court_profile);
        court_context.setName("陈末末");
        court_context.setTime("1月18日 19:00");
        court_context.setInformation("5V5交流赛，欢迎切磋");
        list.add(court_context);
        initRecyclerView();
        initContext();



        return view;
    }

    public void requestData(){
        mRetrofit = new RetrofitBase().getRetrofit();
        // 步骤5:创建网络请求接口对象实例
        Api api = mRetrofit.create(Api.class);
        //步骤6：对发送请求进行封装，传入接口参数
        Call<Article> jsonDataCall = api.getAttentionData(RetrofitBase.mobileToken,RetrofitBase.uid);
//        RetrofitBase.mobileToken,RetrofitBase.uid
//        Call<Article> cloneCall = jsonDataCall.clone();
        //同步执行
        //步骤7:发送网络请求(异步)
        Log.d("uriui","get == url：" + jsonDataCall.request().url());
        jsonDataCall.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                //步骤8：请求处理,输出结果
                Toast.makeText(getActivity(), "更新成功！", Toast.LENGTH_SHORT).show();
//                Data<Article> body = response.body();
//                if (body == null) return;
//                Article article = body.getData();
//                if (article == null) return;
                if( response.body().getMsg()!=null){
                    for (int i = 0;i<response.body().getData().size();i++){
                        Court_Context court_context = new Court_Context();
                        court_context.setAddress(response.body().getData().get(i).getAddress());
//                        court_context.setProfile(response.body().getData().get(i).getHead_sculpture());
                        Log.d("profile",response.body().getData().get(i).getHead_sculpture()+"");
                        court_context.setName(response.body().getData().get(i).getUsername());
                        court_context.setTime(response.body().getData().get(i).getTime());
                        court_context.setInformation(response.body().getData().get(i).getContent());
                        list.add(court_context);
                    }
                    initRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(getActivity(), "get回调失败", Toast.LENGTH_SHORT).show();
            }
        });
//        jsonDataCall.cancel(); //取消请求
//            cloneCall.cancel(); //取消请求
    }

    private CourtAdapter.OnItemClickListener clickListener = new CourtAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (v.getId() == R.id.court_rob) {//对item进行判断如果是第一个那么我们进行跳转反之则提示消息
//                    if(position==0) {//这里position用于判断item是第几个条目然后我们对其设置就可以跳转了。
                Intent intent = new Intent(getActivity(), RobActivity.class);
                intent.putExtra("content",list.get(position).getInformation());
                intent.putExtra("time",list.get(position).getTime());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("address",list.get(position).getAddress());
                intent.putExtra("profile",list.get(position).getProfile());
                startActivity(intent);
                //                    }
//                    else{
//                        Toast.makeText(MainActivity.this, "你点击了同意按钮" + (position + 1), Toast.LENGTH_SHORT).show();
//                    }
//
//                    break;
//                case R.id.btn_refuse:
//                    Toast.makeText(MainActivity.this, "你点击了拒绝按钮" + (position + 1), Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    Toast.makeText(MainActivity.this, "你点击了item按钮" + (position + 1), Toast.LENGTH_SHORT).show();
//                    break;
            }

        }
    };
    @SuppressLint("ResourceAsColor")
    public void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.dynamic_recycler);
        CourtAdapter adapter = new CourtAdapter(list);
        adapter.setOnItemClickListener(clickListener);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.classic);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        list.clear();
////                        requestData();
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                },500);
//            }
//        });

    }
    public class MyLinearLayoutManager extends LinearLayoutManager {
        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                return super.scrollVerticallyBy(dy, recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            return 0;
        }

    }

    public void initContext() {
        for (int i = 0; i < 20; i++) {
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
