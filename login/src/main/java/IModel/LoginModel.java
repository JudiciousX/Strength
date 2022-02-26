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

import com.example.commlib.RetrofitBase;

import IClass.Login_StateClass;
import Request.ILoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginModel implements ILoginModel {
    //登录
    @Override
    public void result(String phoneNumbers, String password, String mobileToken, Context context, Handler handler) {
        //网络请求
        Retrofit retrofit = new RetrofitBase().getRetrofit();

        ILoginRequest loginRequest = retrofit.create(ILoginRequest.class);

        loginRequest.getCall(phoneNumbers, password, mobileToken).enqueue(new Callback<Login_StateClass>() {
            @Override
            public void onResponse(Call<Login_StateClass> call, Response<Login_StateClass> response) {
                Message message = new Message();
                Log.d("TAG", response.body().getCode());
                if(response.body().getCode().equals("200")) {
                    message.obj = response.body().getData();
                }else {
                    message.obj = "Login" + response.body().getCode();
                }
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<Login_StateClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });
    }

    //获取手机唯一标识符
    @Override
    public String getIMEIDeviceId(Context context) {
        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                }else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        Log.d("deviceId", deviceId);
        return deviceId;
    }
}