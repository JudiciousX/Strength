package Tool;

import Adapter.Personal_Adapter;
import IClass.IClass1;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import IClass.IClass2;
import com.example.commlib.RetrofitBase;
import com.example.court.Article;
import com.example.personal.PersonalActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
//         Retrofit retrofit = new RetrofitBase().getRetrofit();
//         NameRequest nameRequest = retrofit.create(NameRequest.class);
//         Gson gson = new Gson();
//         String userInfoBean = gson.toJson(Personal_Fragment.dataClass, User.class);
//         RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfoBean);
//         nameRequest.getlothers(RetrofitBase.mobileToken, RetrofitBase.uid, body).enqueue(new Callback<IClass0>() {
//             @Override
//             public void onResponse(Call<IClass0> call, Response<IClass0> response) {
//                 Message message = new Message();
//                 message.obj = response.body().getCode();
//                 handler.sendMessage(message);
//             }
//
//             @Override
//             public void onFailure(Call<IClass0> call, Throwable t) {
//                 Log.d("TAG", "请求失败");
//             }
//         });
         Message message = new Message();
         message.obj = "200";
         handler.sendMessage(message);
    }

    public static void Request2(Handler handler) {
//        Retrofit retrofit = new RetrofitBase().getRetrofit();
//        NameRequest nameRequest = retrofit.create(NameRequest.class);
//        Gson gson = new Gson();
//        String userInfoBean = gson.toJson(Personal_Fragment.dataClass, User.class);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfoBean);
//        Log.d("scout", Personal_Fragment.dataClass.getLabel());
//        nameRequest.getupdatelabel(RetrofitBase.mobileToken, RetrofitBase.uid, body).enqueue(new Callback<IClass0>() {
//            @Override
//            public void onResponse(Call<IClass0> call, Response<IClass0> response) {
//                Message message = new Message();
//                message.obj = response.body().getCode();
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onFailure(Call<IClass0> call, Throwable t) {
//                Log.d("TAG", "请求失败");
//            }
//        });
        Message message = new Message();
        message.obj = "200";
        handler.sendMessage(message);
    }

    public static void Request1(Handler handler) {
//        Retrofit retrofit = new RetrofitBase().getRetrofit();
//        NameRequest nameRequest = retrofit.create(NameRequest.class);
//        nameRequest.getinfo(RetrofitBase.mobileToken, RetrofitBase.uid).enqueue(new Callback<IClass1>() {
//            @Override
//            public void onResponse(Call<IClass1> call, Response<IClass1> response) {
//                Message message = new Message();
//                message.obj = response.body().getData();
//                handler.sendMessage(message);
//
//            }
//
//            @Override
//            public void onFailure(Call<IClass1> call, Throwable t) {
//                Log.d("TAG", "请求失败");
//            }
//        });
        Message message = new Message();
        message.obj = "000";
        handler.sendMessage(message);
    }

    public static void Request3() {
//         Retrofit retrofit = new RetrofitBase().getRetrofit();
//         NameRequest nameRequest = retrofit.create(NameRequest.class);
//         nameRequest.getFollow(RetrofitBase.mobileToken, RetrofitBase.uid).enqueue(new Callback<User1>() {
//             @Override
//             public void onResponse(Call<User1> call, Response<User1> response) {
//                 Personal_Adapter.list = response.body().getData();
//             }
//
//             @Override
//             public void onFailure(Call<User1> call, Throwable t) {
//                 Log.d("TAG", "请求失败");
//             }
//         });
        User1.ArticleUser user1 = new User1.ArticleUser("13389106579", "CCCJJz_", "http://a1.qpic.cn/psc?/V10JG2ek3hqAnT/ruAMsa53pVQWN7FLK88i5r.79Xn0Bn3vTtBqRcD8czKSSO2mXDYx*s7PJJ5MbwuwVl5WVhS3wS9HaJywcefLyrRj5pbP7Xyfk44Xl*HBlmo!/b&ek=1&kp=1&pt=0&bo=8ADwAPAA8AABFzA!&tl=3&vuin=1119833684&tm=1686326400&dis_t=1686328119&dis_k=bfa380e420984ee4035a5fa046e36161&sce=60-1-1&rf=viewer_4");
        User1.ArticleUser user2 = new User1.ArticleUser("13389106598", "WC57", "http://m.qpic.cn/psc?/V10JG2ek3hqAnT/ruAMsa53pVQWN7FLK88i5h9VNGcdnk6x.mjCHYEFmNrmiwqwsLpGCyhkGvnR9bVRnV0b2qcCdvear8F6k30aFF06dlKA6QDf*EM6eYej50Y!/b&bo=NwSTBDcEkwQBFzA!&rf=viewer_4");
        User1.ArticleUser user3 = new User1.ArticleUser("12345678912", "OoOz", "http://m.qpic.cn/psc?/V10JG2ek3hqAnT/ruAMsa53pVQWN7FLK88i5h9VNGcdnk6x.mjCHYEFmNrFCTawrJhMvn4ziJz7jIbDI.PlPmTvGOW19EiJEWxvPtD5IqMj*JGT.HjSW8eR2Rg!/b&bo=AATrAwAE6wMBFzA!&rf=viewer_4");
        ArrayList list = new ArrayList();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        Personal_Adapter.list = list;
    }

    public static void Request4() {

    }

}
