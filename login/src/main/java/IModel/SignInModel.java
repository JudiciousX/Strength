package IModel;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.commlib.RetrofitBase;

import IClass.SendSmsClass;
import IClass.SignInClass;
import Request.ISendSmsRequest;
import Request.ISignInRequest;
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

        ISendSmsRequest sendSmsRequest = retrofit.create(ISendSmsRequest.class);

        sendSmsRequest.getCall(phoneNumbers).enqueue(new Callback<SendSmsClass>() {
            @Override
            public void onResponse(Call<SendSmsClass> call, Response<SendSmsClass> response) {
                Message message = new Message();
                message.obj = "SendSms" + response.body().getCode();
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<SendSmsClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });

    }

    @Override
    public void SignIn(String phoneNumbers, String password, String token, Handler handler) {
        //网络请求
        Retrofit retrofit = new RetrofitBase().getRetrofit();

        ISignInRequest signInRequest = retrofit.create(ISignInRequest.class);

        signInRequest.getCall(phoneNumbers, password, token).enqueue(new Callback<SignInClass>() {
            @Override
            public void onResponse(Call<SignInClass> call, Response<SignInClass> response) {
                Message message = new Message();
                message.obj = "SignIn" + response.body().getCode();
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<SignInClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });

    }
}
