package com.example.basketball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commlib.RetrofitBase;

import IClass.StateClass;
import IModel.LoginModel;
import Request.IStateRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private AppCompatTextView countDownText;
    private CountDownTimer timer;
    private String mobileToken;
    private Context context = this;
    private String code = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreenConfig();
        setContentView(R.layout.activity_main);
        countDownText = findViewById(R.id.tv_count_down);
        ARouter.getInstance().inject(this);

        countDownText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin();
            }
        });

        initCountDown();

    }

    private void initCountDown() {
        //倒计时总时长 倒计时间间隔
        timer = new CountDownTimer(1000 * 3, 1000) {
            @Override
            public void onTick(long l) {
                if(!isFinishing()) {
                    countDownText.setText(l / 1000 + "跳过");
                }
            }

            @Override
            public void onFinish() {
                if(!isFinishing()) {
                    isLogin();
                }

            }
        }.start();
    }

    //全屏显示
    protected void fullScreenConfig() {
        //去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏 如电量 wifi等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    //销毁timer
    public void destroyTimer() {
        //避免内存泄漏
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    //判断是否登录
    public void isLogin() {
        mobileToken = new LoginModel().getIMEIDeviceId(context);
        Log.d("TAG", mobileToken);
        //网络请求判断是否登录
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        IStateRequest request = retrofit.create(IStateRequest.class);

        request.getCall(mobileToken).enqueue(new Callback<StateClass>() {
            @Override
            public void onResponse(Call<StateClass> call, Response<StateClass> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        code = response.body().getCode();
                        Log.d("TAG", code);
                        if(code.equals("200")) {
                            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                            editor.putString("phoneNumbers", response.body().getMsg());
                            editor.putString("code", code);
                            editor.apply();
                            ARouter.getInstance().build("/message/message").navigation();
                        }else {
                            ARouter.getInstance().build("/login/login").navigation();
                        }
                        destroyTimer();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<StateClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });


    }
}