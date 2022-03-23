package Request;



import IClass.SignIn_Logoff_Forget_SendSmsClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISignInRequest {

    @GET("login/register/{phoneNumbers}/{password}/{token}")
    Call<SignIn_Logoff_Forget_SendSmsClass> getCall(@Path("phoneNumbers")String phone,
                                                    @Path("password")String password,
                                                    @Path("token")String token);
}
