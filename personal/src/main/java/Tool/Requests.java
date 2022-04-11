package Tool;

import IClass.IClass1;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import IClass.IClass2;
import com.example.commlib.RetrofitBase;
import com.example.personal.PersonalActivity;
import com.google.gson.Gson;

import Fragments.Personal_Fragment;
import IClass.IClass0;
import IRequest.NameRequest;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Requests {
     public static void Request(Handler handler) {
         Retrofit retrofit = new RetrofitBase().getRetrofit();
         NameRequest nameRequest = retrofit.create(NameRequest.class);
         Gson gson = new Gson();
         String userInfoBean = gson.toJson(Personal_Fragment.dataClass, User.class);
         RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfoBean);
         nameRequest.getlothers(RetrofitBase.mobileToken, RetrofitBase.uid, body).enqueue(new Callback<IClass0>() {
             @Override
             public void onResponse(Call<IClass0> call, Response<IClass0> response) {
                 Message message = new Message();
                 message.obj = response.body().getCode();
                 handler.sendMessage(message);
             }

             @Override
             public void onFailure(Call<IClass0> call, Throwable t) {
                 Log.d("TAG", "请求失败");
             }
         });
    }

    public static void Request2(Handler handler) {
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        NameRequest nameRequest = retrofit.create(NameRequest.class);
        Gson gson = new Gson();
        String userInfoBean = gson.toJson(Personal_Fragment.dataClass, User.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfoBean);
        Log.d("scout", Personal_Fragment.dataClass.getLabel());
        nameRequest.getupdatelabel(RetrofitBase.mobileToken, RetrofitBase.uid, body).enqueue(new Callback<IClass0>() {
            @Override
            public void onResponse(Call<IClass0> call, Response<IClass0> response) {
                Message message = new Message();
                message.obj = response.body().getCode();
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<IClass0> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });
    }

    public static void Request1(Handler handler) {
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        NameRequest nameRequest = retrofit.create(NameRequest.class);
        nameRequest.getinfo(RetrofitBase.mobileToken, RetrofitBase.uid).enqueue(new Callback<IClass1>() {
            @Override
            public void onResponse(Call<IClass1> call, Response<IClass1> response) {
                Message message = new Message();
                message.obj = response.body().getData();
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(Call<IClass1> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });
    }

}
