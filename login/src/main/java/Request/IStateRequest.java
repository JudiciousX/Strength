package Request;

import IClass.StateClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IStateRequest {

    @GET("login/state/{mobileToken}")
    Call<StateClass> getCall(@Path("mobileToken")String mobileToken);
}
