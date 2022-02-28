package Request;

import IClass.Login_StateClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IStateRequest {

    @GET("login/state/{mobileToken}")
    Call<Login_StateClass> getCall(@Path("mobileToken")String mobileToken);
}
