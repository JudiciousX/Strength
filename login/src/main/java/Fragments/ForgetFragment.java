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

import androidx.fragment.app.FragmentTransaction;

import com.example.login.R;

import IPresenter.SignPresenter;
import IView.ISignView;
import Tools.TimeCount;

public class ForgetFragment extends BaseFragment implements View.OnClickListener, ISignView {
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

            Log.d("TAG", msg.obj.toString());
        }
    };

    public ForgetFragment(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
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
            Send(phoneNumbers, timeCount, context);
        }else if(view.getId() == R.id.Determine_text_1) {
            phoneNumbers = user.getText().toString();
            token = tokenEdit.getText().toString();
            fragmentTransaction.replace(R.id.sign_frame, new AmendFragment(activity)).commit();

        }
    }


    @Override
    public void Succeed(String phoneNumbers, String password, String mobileToken, Context context) {
        //注册
        //presenter.getModel().SignIn(phoneNumbers, password, mobileToken, handler);

        //验证验证码

        //跳转修改密码页面
        fragmentTransaction.replace(R.id.sign_frame, new AmendFragment(activity)).commit();

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
}
