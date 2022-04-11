package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.example.login.R;

import IPresenter.SignPresenter;
import IView.IForgetView;
import IView.ISignView;
import Tools.TimeCount;

//忘记密码
public class ForgetFragment extends BaseFragment implements View.OnClickListener, ISignView, IForgetView {
    private ImageButton back;
    private EditText user;
    private String phoneNumbers;
    private EditText tokenEdit;
    private String token;
    private TextView send;
    private FragmentTransaction fragmentTransaction;
    private TextView Determine;
    private TimeCount timeCount;
    private Activity activity;
    private SignPresenter presenter = new SignPresenter(this);
    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case "Verify200":
                    fragmentTransaction.replace(R.id.sign_frame, new AmendFragment(activity, context,phoneNumbers)).commit();
                    break;
                case "Verify500":
                    toast("服务器异常");
                    break;
                case "Verify400":
                    toast("验证码错误");
                    break;
                case "Verify410":
                    toast("验证码过期");
                    break;
                default:
                    toast("验证码或手机号填写错误");
                    break;
            }
        }
    };

    public ForgetFragment(Activity activity, Context context) {
        this.activity = activity;
        this.context = context.getApplicationContext();
    }


    @Override
    int getLayout() {
        return R.layout.fragment_forget_password;
    }

    @Override
    void initView(View view) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        back = view.findViewById(R.id.back_button_1);
        back.setOnClickListener(this);
        user = view.findViewById(R.id.user_edit_1);
        tokenEdit = view.findViewById(R.id.token_edit_1);
        send = view.findViewById(R.id.send_button_1);
        send.setOnClickListener(this);
        Determine = view.findViewById(R.id.Determine_text_1);
        Determine.setOnClickListener(this);
        timeCount = new TimeCount(60000, 1000, send);
    }

    @Override
    void init() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_button_1) {
            activity.finish();
        }else if(view.getId() == R.id.send_button_1) {
            //发送验证码
            phoneNumbers = user.getText().toString();
            if (phoneNumbers.length() > 11) {
                toast("请输入正确的号码");
            }else {
                Send(phoneNumbers, timeCount, context);
            }

        }else if(view.getId() == R.id.Determine_text_1) {
            phoneNumbers = user.getText().toString();
            token = tokenEdit.getText().toString();

            if(token.length() == 0 || phoneNumbers.length() == 0) {
                toast("验证码或手机号未输入");
            }else {
                verify(phoneNumbers, token, handler);
            }

        }
    }


    @Override
    public void Succeed(String phoneNumbers, String password, String mobileToken, Context context) {
    }

    @Override
    public void Send(String phoneNumbers, TimeCount timeCount, Context context) {
        //发送验证码
        presenter.getModel().Sends(phoneNumbers, handler);

        //倒计时
        timeCount.start();
    }

    @Override
    public void Fail(int id, EditText editText) {

    }

    @Override
    public void verify(String phoneNumbers, String mobileToken, Handler handler) {
        presenter.getModel2().verify(phoneNumbers, token, handler);
    }

    @Override
    public void amend(String phoneNumbers, String password, Handler handler) {
    }

    public void toast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
