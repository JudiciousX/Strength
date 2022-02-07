package Request;



import IClass.SignInClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISignInRequest {

    @GET("login/register/{phoneNumbers}/{password}/{token}")
    Call<SignInClass> getCall(@Path("phoneNumbers")String phone,
                              @Path("password")String password,
                              @Path("token")String token);
}
