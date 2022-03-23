package IModel;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.commlib.RetrofitBase;

import IClass.SignIn_Logoff_Forget_SendSmsClass;
import Request.IRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInModel implements ISignModel{
    //发送验证码
    @Override
    public void Sends(String phoneNumbers, Handler handler) {
        //网络请求
        Retrofit retrofit = new RetrofitBase().getRetrofit();

        IRequests sendSmsRequest = retrofit.create(IRequests.class);

        sendSmsRequest.getSendSms(phoneNumbers).enqueue(new Callback<SignIn_Logoff_Forget_SendSmsClass>() {
            @Override
            public void onResponse(Call<SignIn_Logoff_Forget_SendSmsClass> call, Response<SignIn_Logoff_Forget_SendSmsClass> response) {
                Message message = new Message();
                message.obj = "SendSms" + response.body().getCode();
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<SignIn_Logoff_Forget_SendSmsClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });

    }

    //注册
    @Override
    public void SignIn(String phoneNumbers, String password, String token, Handler handler) {
        //网络请求
        Retrofit retrofit = new RetrofitBase().getRetrofit();

        IRequests signInRequest = retrofit.create(IRequests.class);

        signInRequest.getSignIn(phoneNumbers, password, token).enqueue(new Callback<SignIn_Logoff_Forget_SendSmsClass>() {
            @Override
            public void onResponse(Call<SignIn_Logoff_Forget_SendSmsClass> call, Response<SignIn_Logoff_Forget_SendSmsClass> response) {
                Message message = new Message();
                message.obj = "SignIn" + response.body().getCode();
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<SignIn_Logoff_Forget_SendSmsClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });

    }
}
