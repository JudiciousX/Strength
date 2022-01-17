package com.example.login;

public class LoginModel implements ILoginModel{
    @Override
    public String result(String id, String password) {
        //处理账号超过长度问题
        if(id.length() > 11) {
            return "error";
        }
        //网络请求
        //网络请求结果
        String login_result = new String();
        return login_result;
    }
}
