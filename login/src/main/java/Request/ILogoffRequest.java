package Request;

import IClass.SignIn_Logoff_Forget_SendSmsClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ILogoffRequest {

    @GET("login/logoff/{mobileToken}")
    Call<SignIn_Logoff_Forget_SendSmsClass> getCall(@Path("mobileToken")String mobileToken);
}
