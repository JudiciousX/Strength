package IRequest;

import org.json.JSONObject;

import IClass.NameClass;
import Tool.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NameRequest {


    @POST("info/updatename")
    Call<NameClass> getCall(@Body String Body);
}
