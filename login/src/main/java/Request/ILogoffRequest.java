package Request;

import IClass.LogoffClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ILogoffRequest {

    @GET("login/logoff/{mobileToken}")
    Call<LogoffClass> getCall(@Path("mobileToken")String mobileToken);
}
