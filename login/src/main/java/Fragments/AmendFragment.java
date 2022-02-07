package Fragments;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.login.R;

public class AmendFragment extends BaseFragment implements View.OnClickListener{
    private ImageButton back;
    private EditText newPassword1;
    private EditText newPassword2;
    private TextView determine;
    private String password1;
    private String password2;
    private Activity activity;

    public AmendFragment(Activity activity) {
        this.activity = activity;
    }
    @Override
    int getLayout() {
        return R.layout.fragment_amend_password;
    }

    @Override
    void initView(View view) {
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
        }else if(view.getId() == R.id.Determine_text_2) {
            password1 = newPassword1.getText().toString();
            password2 = newPassword2.getText().toString();

            //修改密码


        }
        activity.finish();
    }
}
