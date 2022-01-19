package com.example.login;

import android.content.Context;

public interface ILoginModel {
    //网络请求 获取登陆结果
    String result(String id, String password);

    //获得手机唯一标识符id
    String getIMEIDeviceId(Context context);
}
