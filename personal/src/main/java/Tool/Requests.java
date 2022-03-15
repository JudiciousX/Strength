package Tool;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.commlib.RetrofitBase;
import com.google.gson.Gson;

import Fragments.Personal_Fragment;
import IClass.IClass;
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
        Log.d("xxxxx", userInfoBean);
        nameRequest.getName(body).enqueue(new Callback<IClass>() {
            @Override
            public void onResponse(Call<IClass> call, Response<IClass> response) {
                Message message = new Message();
                message.obj = response.body().getCode();
                Log.d("xxxxxx", response.body().getCode());
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<IClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });
    }
}
