package IView;

import android.content.Context;
import android.widget.EditText;

import Tools.TimeCount;

public interface ISignView {
    //注册成功
    void Succeed(String phoneNumbers, String password, String mobileToken, Context context);
    //发送验证码
    void Send(String phoneNumbers, TimeCount timeCount, Context context);
    //注册失败
    void Fail(int id, EditText editText);
}
