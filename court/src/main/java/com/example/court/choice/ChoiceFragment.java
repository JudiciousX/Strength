package com.example.court.choice;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.court.Api;
import com.example.court.Article;
import com.example.court.CourtAdapter;
import com.example.court.Court_Context;
import com.example.court.R;
import com.example.court.RobActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.alibaba.android.arouter.compiler.utils.Consts.TAG;

public class ChoiceFragment extends Fragment {
    private View view;
    private Retrofit mRetrofit;
    private TextView textView;


    private List<Court_Context> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.court_recyclerview, container, false);
//        mRetrofit = new Retrofit.Builder()
//                //设置网络请求BaseUrl地址
//                .baseUrl("http://47.116.14.251:8888")
//                //设置数据解析器
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        // 步骤5:创建网络请求接口对象实例
//        Api api = mRetrofit.create(Api.class);
//        //步骤6：对发送请求进行封装，传入接口参数
//        Call<Article> jsonDataCall = api.getJsonData("944348013390725120");
////        Call<Article> cloneCall = jsonDataCall.clone();
//        //同步执行
//        //步骤7:发送网络请求(异步)
//        Log.d("uriui","get == url：" + jsonDataCall.request().url());
//        jsonDataCall.enqueue(new Callback<Article>() {
//            @Override
//            public void onResponse(Call<Article> call, Response<Article> response) {
//                //步骤8：请求处理,输出结果
//                Toast.makeText(getActivity(), "get回调成功:异步执行", Toast.LENGTH_SHORT).show();
////                Data<Article> body = response.body();
////                if (body == null) return;
////                Article article = body.getData();
////                if (article == null) return;
//                if( response.body().getMsg()!=null){
//                    Court_Context court_context = new Court_Context();
//                    court_context.setAddress(response.body().getData().getAddress());
//                    court_context.setProfile(R.drawable.court_profile);
//                    court_context.setName("陈末末");
//                    court_context.setTime("1月18日 19:00");
//                    court_context.setInformation("5V5交流赛，欢迎切磋");
//                    list.add(court_context);
//                    initRecyclerView();
//                    Log.d("addd",list.get(0).getAddress());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Article> call, Throwable t) {
//                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
//                Toast.makeText(getActivity(), "get回调失败", Toast.LENGTH_SHORT).show();
//            }
//        });
// later...
//            jsonDataCall.cancel(); //取消请求
//            cloneCall.cancel(); //取消请求
//            Court_Context court_context = new Court_Context();
//            court_context.setProfile(R.drawable.court_profile);
//            court_context.setName("陈末末");
//            court_context.setTime("1月18日 19:00");
//            court_context.setInformation("5V5交流赛，欢迎切磋");
//            list.add(court_context);
            initRecyclerView();
            initContext();



        return view;
    }

    private CourtAdapter.OnItemClickListener clickListener = new CourtAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (v.getId() == R.id.court_rob) {//对item进行判断如果是第一个那么我们进行跳转反之则提示消息
//                    if(position==0) {//这里position用于判断item是第几个条目然后我们对其设置就可以跳转了。
                Intent intent = new Intent(getActivity(), RobActivity.class);
                intent.putExtra("profile", R.drawable.court_user);
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

    /**
     * 示例，get加载Json数据
     */
    private void getJsonData() {
        // 步骤5:创建网络请求接口对象实例
        Api api = mRetrofit.create(Api.class);
        //步骤6：对发送请求进行封装，传入接口参数
        Call<Article> jsonDataCall = api.getJsonData("944348013390725120");

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
                    response.body().getData().getAddress();

                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(getActivity(), "get回调失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.dynamic_recycler);
        CourtAdapter adapter = new CourtAdapter(list);
       adapter.setOnItemClickListener(clickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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
