package Request;

import IClass.SignIn_Logoff_Forget_SendSmsClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISendSmsRequest {

    @GET("login/sendsms/{phoneNumbers}")
    Call<SignIn_Logoff_Forget_SendSmsClass> getCall(@Path("phoneNumbers")String phoneNumbers);
}
