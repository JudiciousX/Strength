package Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commlib.IMEIDeviceId;
import com.example.commlib.RetrofitBase;
import com.example.personal.R;

import IClass.SignIn_Logoff_Forget_SendSmsClass;
import IModel.LoginModel;
import Request.IRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//兴趣标签
public class more_Fragment extends Fragment {
    private TextView textView;
    private Button back;
    private String mobileToken;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case "200":
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("LoginId", Context.MODE_PRIVATE).edit();
                    editor.clear();
                    editor.putBoolean("is_Login", false);
                    editor.apply();
                    //退出登录并返回登录页面
                    ARouter.init(getActivity().getApplication()); // 尽可能早，推荐在Application中初始化ARouter
                    ARouter.getInstance().inject(this);
                    ARouter.getInstance().build("/login/login").navigation();
                    getActivity().finish();
                    break;
                case "500":
                    Toast.makeText(getContext(), "服务器异常，修改失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        textView = view.findViewById(R.id.more_exit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //网络请求
                mobileToken = RetrofitBase.mobileToken;
                Retrofit retrofit = new RetrofitBase().getRetrofit();
                IRequests iRequests = retrofit.create(IRequests.class);
                iRequests.getLogoff(mobileToken).enqueue(new Callback<SignIn_Logoff_Forget_SendSmsClass>() {
                    @Override
                    public void onResponse(Call<SignIn_Logoff_Forget_SendSmsClass> call, Response<SignIn_Logoff_Forget_SendSmsClass> response) {
                        Message message = new Message();
                        message.obj = response.body().getCode();
                        Log.d("scout", response.toString());
                        Log.d("scout", response.body().getCode());
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Call<SignIn_Logoff_Forget_SendSmsClass> call, Throwable t) {
                        Log.d("TAG", "请求失败");
                    }
                });

            }
        });
        back = view.findViewById(R.id.more_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
