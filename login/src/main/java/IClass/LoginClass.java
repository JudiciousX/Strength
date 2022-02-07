package IClass;

import android.util.Log;

public class LoginClass {
    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public void show() {
        Log.d("TAG", getMsg());
        Log.d("TAG", getCode());
    }
}
