package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
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

public class AmendFragment extends BaseFragment implements View.OnClickListener, ISignView,IForgetView {
    private ImageButton back;
    private EditText newPassword1;
    private EditText newPassword2;
    private TextView determine;
    private FragmentTransaction fragmentTransaction;
    private String password1;
    private String password2;
    private Activity activity;
    private String phoneNumber;
    private Context context;
    private SignPresenter presenter = new SignPresenter(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case "Forget200":
                    activity.finish();
                    break;
                case "Forget500":
                    Toast.makeText(context, "服务器异常，修改失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public AmendFragment(Activity activity, Context context, String phoneNumber) {
        this.activity = activity;
        this.context = context;
        this.phoneNumber = phoneNumber;
    }
    @Override
    int getLayout() {
        return R.layout.fragment_amend_password;
    }

    @Override
    void initView(View view) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        back = view.findViewById(R.id.back_button_2);
        newPassword1 = view.findViewById(R.id.password_edit_2);
        newPassword2 = view.findViewById(R.id.password1_edit_2);
        determine = view.findViewById(R.id.Determine_text_2);
    }

    @Override
    void init() {
        back.setOnClickListener(this);
        determine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back_button_2) {
            activity.finish();
        }else if(view.getId() == R.id.Determine_text_2) {
            password1 = newPassword1.getText().toString();
            password2 = newPassword2.getText().toString();
            if (password1.equals(password2)) {
                amend(phoneNumber, password1, handler);
            }else {
                Toast.makeText(context, "两次密码不一致", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void verify(String phoneNumbers, String mobileToken, Handler handler) {

    }

    @Override
    public void amend(String phoneNumbers, String password, Handler handler) {
        presenter.getModel2().amend(phoneNumbers, password, handler);
    }

    @Override
    public void Succeed(String phoneNumbers, String password, String mobileToken, Context context) {

    }

    @Override
    public void Send(String phoneNumbers, TimeCount timeCount, Context context) {

    }

    @Override
    public void Fail(int id, EditText editText) {

    }
}
