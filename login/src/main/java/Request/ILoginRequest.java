package Request;

import IClass.Login_StateClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ILoginRequest {

    @GET("login/logon/{phoneNumbers}/{password}/{mobileToken}")
    Call<Login_StateClass> getCall(@Path("phoneNumbers")String user,
                                   @Path("password")String password,
                                   @Path("mobileToken")String token);

}
