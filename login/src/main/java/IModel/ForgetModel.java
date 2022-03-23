package IModel;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.commlib.RetrofitBase;

import IClass.Login_StateClass;
import IClass.SignIn_Logoff_Forget_SendSmsClass;
import Request.IRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetModel implements IForgetModel{
    //验证手机号码
    @Override
    public void verify(String phoneNumbers, String mobileToken, Handler handler) {
        //网络请求
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        IRequests requests = retrofit.create(IRequests.class);
        requests.getVerify(phoneNumbers, mobileToken).enqueue(new Callback<SignIn_Logoff_Forget_SendSmsClass>() {
            @Override
            public void onResponse(Call<SignIn_Logoff_Forget_SendSmsClass> call, Response<SignIn_Logoff_Forget_SendSmsClass> response) {
                Message message = new Message();
                message.obj = "Verify" + response.body().getCode();
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<SignIn_Logoff_Forget_SendSmsClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });
    }

    //修改密码
    @Override
    public void amend(String phoneNumbers, String password, Handler handler) {
        //网络请求
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        IRequests forgetRequest = retrofit.create(IRequests.class);
        forgetRequest.getForget(phoneNumbers, password).enqueue(new Callback<Login_StateClass>() {
            @Override
            public void onResponse(Call<Login_StateClass> call, Response<Login_StateClass> response) {
                Message message = new Message();
                message.obj = "Forget" + response.body().getCode();
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<Login_StateClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });

    }
}
