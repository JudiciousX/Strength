package com.example.login;

public interface ILoginModel {
    //网络请求 获取登陆结果
    String result(String id, String password);
}
