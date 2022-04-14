package com.example.court.attention;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    Court_Context court_context = new Court_Context();
    private Context context;
    private Activity activity;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Court_Context> list = new ArrayList<>();

//    public AttentionFragment(Context context, Activity activity) {
////        this.context = context;
////        this.activity = activity;
////
////    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.court_recyclerview,container,false);
         mRetrofit = new RetrofitBase().getRetrofit();

        for(int i = 0 ;i<4;i++){
            court_context.setName("陈末末");
            court_context.setAddress("抛物线篮球场");
            court_context.setTime("1月18日 19:00");
            court_context.setInformation("5V5交流赛，欢迎切磋");
            list.add(court_context);
        }
        RecyclerView recyclerView = view.findViewById(R.id.dynamic_recycler);
        CourtAdapter adapter = new CourtAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
    /**
     * 示例，get加载Json数据
     */
    private void getJsonData() {
        // 步骤5:创建网络请求接口对象实例
        Api api = mRetrofit.create(Api.class);
        //步骤6：对发送请求进行封装，传入接口参数

        Call<Article> jsonDataCall = api.getJsonData("ac9742f7e13c68d4","944348013390725120");

        //同步执行
//         Response<Data<Info>> execute = jsonDataCall.execute();

        //步骤7:发送网络请求(异步)
        Log.d("uriui","get == url：" + jsonDataCall.request().url());
        jsonDataCall.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                //步骤8：请求处理,输出结果
                Toast.makeText(getActivity(), "get回调成功:异步执行", Toast.LENGTH_SHORT).show();
//                Data<Article> body = response.body();
//                if (body == null) return;
//                Article article = body.getData();
//                if (article == null) return;
                if( response.body().getMsg()!=null){
                    List<Data> article = response.body().getData();
//                    mTextView.setText("返回的数据：" + "\n\n" + response.body().getMsg() + "\n" + response.body().getData().getUsername()+ "\n" +response.body().getData().getAddress()+ "\n" +response.body().getData().getArticle_name()+ "\n" +response.body().getData().getHead_sculpture());
                    court_context.setAddress(article.get(0).getAddress());
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(getActivity(), "get回调失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.dynamic_recycler);
        CourtAdapter adapter = new CourtAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    public void initContext(){
        for(int i = 0 ;i<20;i++){
            Court_Context court_context = new Court_Context();
            court_context.setName("陈末末");
            court_context.setAddress("西安抛物线篮球场");
            court_context.setTime("1月18日 19:00");
            court_context.setInformation("5V5交流赛，欢迎切磋");
            list.add(court_context);
        }
    }
}
