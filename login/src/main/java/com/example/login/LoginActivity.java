package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path="/login/login")
public class LoginActivity extends BaseActivity implements ILoginView{
    private EditText id_edit;
    private EditText password_edit;
    private Button login_button;
    private TextView forget_text;
    private TextView signIn_text;
    private String id;
    private String password;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    void initView() {
        id_edit = (EditText) findViewById(R.id.id_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        login_button = (Button) findViewById(R.id.login_button);
        forget_text = (TextView) findViewById(R.id.forget_text);
        signIn_text = (TextView) findViewById(R.id.signIn_text);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = id_edit.getText().toString();
                password = password_edit.getText().toString();
                boolean b = loginPresenter.isSucceed(id, password);
                if(b == true) {
                    Succeed();
                }else {
                    Failed();
                }
            }
        });
    }


    @Override
    public void Failed() {
        //弹窗（失败信息）
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("登录失败");
        builder.setMessage("账号或密码错误，请重新输入");
        builder.setCancelable(false);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    @Override
    public void Succeed() {
        //跳转到首页
    }
}