package IRequest;

import IClass.IClass0;
import IClass.IClass1;
import IClass.IClass2;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NameRequest {

    @POST("info/updateothers")
    Call<IClass0> getlothers(@Body RequestBody Body);

    @POST("info/getinfo/{uid}")
    Call<IClass1> getinfo(@Path("uid")String uid);

    @POST("info/updatelabel")
    Call<IClass0> getupdatelabel(@Body RequestBody Body);

    @Multipart
    @POST("info/updateback/{uid}")
    Call<IClass0> upload1(@Path("uid")String uid, @Part() MultipartBody.Part file);

    @Multipart
    @POST("info/updatehead/{uid}")
    Call<IClass0> upload2(@Path("uid")String uid, @Part() MultipartBody.Part file);

    @POST("article/getArticle/{uid}")
    Call<IClass2> getArticle(@Path("uid")String uid);
}
