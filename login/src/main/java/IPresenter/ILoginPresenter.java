package IPresenter;

import android.content.Context;
import android.os.Handler;

public interface ILoginPresenter {
    //是否登录成功
    boolean isSucceed(String phoneNumbers, String password, String mobileToken, Context context, Handler handler);
}
