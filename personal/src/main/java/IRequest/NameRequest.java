package IRequest;

import com.example.commlib.IMEIDeviceId;

import Fragments.Personal_Fragment;
import IClass.IClass0;
import IClass.IClass1;
import IClass.IClass2;
import Tool.User1;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NameRequest {

    //修改全部信息
    @POST("info/updateOthers")
    Call<IClass0> getlothers(@Header("mobileToken") String mobileToken, @Header("uid") String uid, @Body RequestBody Body);

    //获取全部信息
    @GET("info/getInfo")
    Call<IClass1> getinfo(@Header("mobileToken") String mobileToken, @Header("uid") String uid);

    //修改标签
    @POST("info/updateLabel")
    Call<IClass0> getupdatelabel(@Header("mobileToken") String mobileToken, @Header("uid") String uid, @Body RequestBody Body);

    //修改背景
    @Multipart
    @POST("info/updateBack")
    Call<IClass0> upload1(@Header("mobileToken") String mobileToken, @Header("uid") String uid, @Part() MultipartBody.Part file);

    //修改头像
    @Multipart
    @POST("info/updateHead")
    Call<IClass0> upload2(@Header("mobileToken") String mobileToken, @Header("uid") String uid, @Part() MultipartBody.Part file);

    //获取关注列表
    @GET("relation/getFollow")
    Call<User1> getFollow(@Header("mobileToken") String mobileToken, @Header("uid") String uid);
}
