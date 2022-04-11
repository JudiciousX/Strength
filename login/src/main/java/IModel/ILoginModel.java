package IModel;

import android.content.Context;
import android.os.Handler;

public interface ILoginModel {
    //网络请求 获取登陆结果
    void result(String phoneNumbers, String password, String mobileToken, Context context, Handler handler);

}
