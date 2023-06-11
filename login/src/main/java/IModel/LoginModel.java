package IModel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.commlib.IMEIDeviceId;
import com.example.commlib.RetrofitBase;

import IClass.Login_StateClass;
import Request.IRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginModel implements ILoginModel {
    //登录
    @Override
    public void result(String phoneNumbers, String password, String mobileToken, Context context, Handler handler) {
        //网络请求
//        Retrofit retrofit = new RetrofitBase().getRetrofit();
//
//        IRequests loginRequest = retrofit.create(IRequests.class);
//        //Log.d("xxxxxx", "login");
//        loginRequest.getLogin(phoneNumbers, password, mobileToken).enqueue(new Callback<Login_StateClass>() {
//            @Override
//            public void onResponse(Call<Login_StateClass> call, Response<Login_StateClass> response) {
//                Message message = new Message();
//                Log.d("xxxxxx", response.body().getCode());
//                if(response.body().getCode().equals("200")) {
//                    message.obj = response.body().getData();
//                }else {
//                    message.obj = "Login" + response.body().getCode();
//                }
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onFailure(Call<Login_StateClass> call, Throwable t) {
//                Log.d("TAG", "请求失败");
//            }
//        });
        Message message = new Message();
        message.obj = "13389106597";
        handler.sendMessage(message);
    }

}
