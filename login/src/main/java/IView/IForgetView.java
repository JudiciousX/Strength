package IView;

import android.os.Handler;

public interface IForgetView {
    //验证手机号
    void verify(String phoneNumbers, String mobileToken, Handler handler);
    //修改密码
    void amend(String phoneNumbers, String password, Handler handler);
}
