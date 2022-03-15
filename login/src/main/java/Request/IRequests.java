package Request;

import IClass.Login_StateClass;
import IClass.SignIn_Logoff_Forget_SendSmsClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRequests {

    @GET("login/logon/{phoneNumbers}/{password}/{mobileToken}")
    Call<Login_StateClass> getLogin(@Path("phoneNumbers")String user,
                                   @Path("password")String password,
                                   @Path("mobileToken")String token);

    @GET("login/logoff/{mobileToken}")
    Call<SignIn_Logoff_Forget_SendSmsClass> getLogoff(@Path("mobileToken")String mobileToken);

    @GET("login/forget/{phoneNumbers}/{password}")
    Call<Login_StateClass> getForget(@Path("phoneNumbers")String user,
                                   @Path("password")String password);

    @GET("login/sendsms/{phoneNumbers}")
    Call<SignIn_Logoff_Forget_SendSmsClass> getSendSms(@Path("phoneNumbers")String phoneNumbers);

    @GET("login/register/{phoneNumbers}/{password}/{token}")
    Call<SignIn_Logoff_Forget_SendSmsClass> getSignIn(@Path("phoneNumbers")String phone,
                                                    @Path("password")String password,
                                                    @Path("token")String token);

    @GET("login/state/{mobileToken}")
    Call<Login_StateClass> getState(@Path("mobileToken")String mobileToken);
}
