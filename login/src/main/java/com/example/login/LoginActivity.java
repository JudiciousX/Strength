package com.example.login;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commlib.RetrofitBase;

import IPresenter.LoginPresenter;
import IView.ILoginView;
import Tools.JumpActivity;

@Route(path="/login/login")
public class LoginActivity extends BaseActivity implements ILoginView, JumpActivity {
    private boolean id_isEmpty = false;
    private boolean password_isEmpty = false;
    private Button see;
    private EditText id_edit;
    private EditText password_edit;
    private Button login_button;
    private TextView forget_text;
    private TextView signIn_text;
    private String phoneNumbers;
    private String password;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case "Login400" :
                    Failed();
                    break;
                default:
                    Succeed();
                    RetrofitBase.uid = msg.obj.toString();
                    SharedPreferences.Editor editor= getSharedPreferences("LoginId", MODE_PRIVATE).edit();
                    editor.putString("uid", msg.obj.toString());
                    editor.putBoolean("is_Login", true);
                    editor.apply();
                    break;

            }
            Log.d("TAG", msg.obj.toString());
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    void initView() {
        context = LoginActivity.this;
        id_edit = (EditText) findViewById(R.id.id_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        login_button = (Button) findViewById(R.id.login_button);
        forget_text = (TextView) findViewById(R.id.forget_text);
        signIn_text = (TextView) findViewById(R.id.signIn_text);
        see = (Button) findViewById(R.id.see_button);
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
                    phoneNumbers = id_edit.getText().toString();
                    password = password_edit.getText().toString();
                    if(password.length() < 6) {
                        Toast.makeText(context, "密码长度不得低于6位", Toast.LENGTH_SHORT).show();
                    } else if(phoneNumbers.length() > 11) {
                        Toast.makeText(context, "账号不得超过11位", Toast.LENGTH_SHORT).show();
                    } else {
                        String mobileToken = loginPresenter.getModel().getIMEIDeviceId(context);
                        loginPresenter.getModel().result(phoneNumbers, password, mobileToken, context, handler);
                    }

                }else {
                    login_button.setBackground(getResources().getDrawable(R.drawable.unlogin));
                    if(id_isEmpty) {
                        Toast.makeText(context, "输入密码", Toast.LENGTH_SHORT).show();
                    }else if(password_isEmpty) {
                        Toast.makeText(context, "输入账号", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "输入账号和密码", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        forget_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到忘记密码
                jump("forget");
            }
        });
        signIn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册
                jump("signIn");
            }
        });
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (see.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.see).getConstantState())) {
                    see.setBackground(getResources().getDrawable(R.drawable.unsee));
                    password_edit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    password_edit.setSelection(password_edit.getText().length());
                } else {
                    see.setBackground(getResources().getDrawable(R.drawable.see));
                    password_edit.setInputType(InputType.TYPE_CLASS_TEXT);
                    password_edit.setSelection(password_edit.getText().length());
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
        ARouter.getInstance().build("/main/main").navigation();
        this.finish();
    }

    @Override
    public boolean isEmpty(boolean id, boolean password) {
        if(id && password) {
            return true;
        }
        return false;
    }

    @Override
    public void jump(String data) {
        Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
        intent.putExtra("type", data);
        startActivity(intent);
    }
}