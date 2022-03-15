package IRequest;

import IClass.IClass;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NameRequest {

    @POST("info/updatename")
    Call<IClass> getName(@Body RequestBody Body);

    @POST("info/updatesign")
    Call<IClass> getsign(@Body RequestBody Body);

    @POST("info/updatelabel")
    Call<IClass> getlabel(@Body RequestBody Body);

    @POST("info/updateothers")
    Call<IClass> getlothers(@Body RequestBody Body);

    @Multipart
    @POST("info/updateback/{uid}")
    Call<IClass> upload1(@Path("uid")String uid, @Part() MultipartBody.Part file);

}
