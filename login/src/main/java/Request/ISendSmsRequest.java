package Request;

import IClass.SendSmsClass;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISendSmsRequest {

    @GET("login/sendsms/{phoneNumbers}")
    Call<SendSmsClass> getCall(@Path("phoneNumbers")String phoneNumbers);
}
