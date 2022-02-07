package IModel;

import android.os.Handler;

public interface ISignModel {
    //发送验证码
    void Sends(String phoneNumbers, Handler handler);
    //注册
    void SignIn(String phoneNumbers, String password, String token, Handler handler);
}
