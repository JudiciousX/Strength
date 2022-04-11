package Fragments;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;

import IPresenter.SignPresenter;
import IView.ISignView;
import Tools.TimeCount;

//注册
public class SignInFragment extends BaseFragment implements View.OnClickListener, ISignView {
    private TimeCount timeCount;
    private Activity activity;
    private ImageButton back;
    private EditText user;
    private String phoneNumbers;
    private EditText tokenEdit;
    private String token;
    private TextView send;
    private EditText password;
    private String password_1;
    private EditText password1;
    private String password_2;
    private RadioButton flag;
    private TextView sign;
    private TextView login;
    private SignPresenter presenter = new SignPresenter(this);
    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case "SendSms403" :
                    Toast.makeText(context, "该账号已被注册", Toast.LENGTH_SHORT).show();
                case "SignIn400" :
                    Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                case "SignIn410" :
                    Toast.makeText(context, "验证码过期", Toast.LENGTH_SHORT).show();
                case "SignIn200" :
                    activity.finish();
            }
            Log.d("TAG", msg.obj.toString());
        }
    };


    public  SignInFragment(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    int getLayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    void initView(View view) {
        back = view.findViewById(R.id.back_button);
        back.setOnClickListener(this);
        user = view.findViewById(R.id.user_edit);
        tokenEdit = view.findViewById(R.id.token_edit);
        send = view.findViewById(R.id.send_button);
        send.setOnClickListener(this);
        password = view.findViewById(R.id.password1_edit);
        password1 = view.findViewById(R.id.password2_edit);
        flag = view.findViewById(R.id.check_button);
        sign = view.findViewById(R.id.sign_text);
        sign.setOnClickListener(this);
        login = view.findViewById(R.id.login_text);
        login.setOnClickListener(this);
        timeCount = new TimeCount(60000, 1000, send);
    }

    @Override
    void init() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button || view.getId() == R.id.login_text) {
            activity.finish();
        }else if(view.getId() == R.id.send_button) {
            phoneNumbers = user.getText().toString();
            //发送验证码
            Send(phoneNumbers, timeCount, context);
        }else if(view.getId() == R.id.sign_text) {
            if(flag.isChecked()) {
                phoneNumbers = user.getText().toString();
                token = tokenEdit.getText().toString();
                password_1 = password.getText().toString();
                password_2 = password1.getText().toString();
                if(password_1.equals(password_2)) {
                    //注册成功
                    Succeed(phoneNumbers, password_1, token, context);
                }else {
                    Fail(1,password1);

                }
            }else {
                Fail(2,password1);
            }
        }
    }

    @Override
    public void Succeed(String phoneNumbers, String password, String mobileToken, Context context) {
        //注册
        presenter.getModel().SignIn(phoneNumbers, password, mobileToken,handler);

    }

    @Override
    public void Send(String phoneNumbers, TimeCount timeCount, Context context) {
        //倒计时
        timeCount.start();

        //发送验证码
        presenter.getModel().Sends(phoneNumbers, handler);
    }

    @Override
    public void Fail(int id, EditText editText) {
        if(id == 1) {
            Toast.makeText(activity, "两次密码输入不同", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }else if(id == 2) {
            Toast.makeText(activity, "请先同意xxx协议", Toast.LENGTH_SHORT).show();
        }
    }

}
