package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path="/login/login")
public class LoginActivity extends BaseActivity implements ILoginView{
    private boolean id_isEmpty = false;
    private boolean password_isEmpty = false;
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
    }

    @Override
    void init() {
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!"".equals(password_edit.getText().toString())) {
                    password_isEmpty = true;
                    if(id_isEmpty) {
                        login_button.setBackground(getResources().getDrawable(R.drawable.login_bg));
                    }
                }else {
                    password_isEmpty = false;
                    login_button.setBackground(getResources().getDrawable(R.drawable.unlogin));
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!"".equals(password_edit.getText().toString())) {
                    password_isEmpty = true;
                    if(id_isEmpty) {
                        login_button.setBackground(getResources().getDrawable(R.drawable.login_bg));
                    }
                }else {
                    password_isEmpty = false;
                    login_button.setBackground(getResources().getDrawable(R.drawable.unlogin));

                }
            }
        });
        id_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!"".equals(id_edit.getText().toString())) {
                    id_isEmpty = true;
                    if(password_isEmpty) {
                        login_button.setBackground(getResources().getDrawable(R.drawable.login_bg));
                    }
                }else {
                    id_isEmpty = false;
                    login_button.setBackground(getResources().getDrawable(R.drawable.unlogin));
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!"".equals(id_edit.getText().toString())) {
                    id_isEmpty = true;
                    if(password_isEmpty) {
                        login_button.setBackground(getResources().getDrawable(R.drawable.login_bg));
                    }
                }else {
                    id_isEmpty = false;
                    login_button.setBackground(getResources().getDrawable(R.drawable.unlogin));
                }
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(id_isEmpty, password_isEmpty)) {
                    id = id_edit.getText().toString();
                    password = password_edit.getText().toString();
                    boolean b = loginPresenter.isSucceed(id, password);
                    if(b) {
                        Succeed();
                    }else {
                        Failed();
                    }
                }else {
                    login_button.setBackground(getResources().getDrawable(R.drawable.unlogin));
                    if(id_isEmpty) {
                        Toast.makeText(LoginActivity.this, "输入密码", Toast.LENGTH_SHORT).show();
                    }else if(password_isEmpty) {
                        Toast.makeText(LoginActivity.this, "输入账号", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "输入账号和密码", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        forget_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到忘记密码

            }
        });
        signIn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册
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
        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.e("TAG", "对话框显示了");
            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e("TAG", "对话框消失了");
            }
        });
        dialog.show();
    }

    @Override
    public void Succeed() {
        //跳转到首页
        ARouter.getInstance().build("/message/message").navigation();
    }

    @Override
    public boolean isEmpty(boolean id, boolean password) {
        if(id && password) {
            return true;
        }
        return false;
    }

}