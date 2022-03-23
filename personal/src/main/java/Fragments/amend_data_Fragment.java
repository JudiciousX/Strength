package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.personal.R;

import java.util.List;

import Tool.DatePickerDialog;
import Tool.DateUtil;
import Tool.Requests;

public class amend_data_Fragment extends Fragment implements View.OnClickListener {
    Context context;
    Button back;
    TextView over;
    TextView birthday;
    private EditText email;
    private EditText signature;
    private RadioGroup radioGroup;
    private EditText username;
    private Dialog dateDialog;
    private FragmentTransaction fragmentTransaction;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case "200":
                    getActivity().onBackPressed();
                    break;
                case "500":
                    Toast.makeText(context, "服务器异常，修改失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.amend_data, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        username = view.findViewById(R.id.tag2);
        username.setText(Personal_Fragment.dataClass.getUsername());
        email = view.findViewById(R.id.tag8);
        email.setText(Personal_Fragment.dataClass.getEmail());
        radioGroup = view.findViewById(R.id.tag6);
        RadioButton radioButton = null;
        if(Personal_Fragment.dataClass.getSex() == 1) {
            radioButton = radioGroup.findViewById(R.id.boy);
        }else {
            radioButton = radioGroup.findViewById(R.id.girl);
        }
        radioButton.setChecked(true);
        signature = view.findViewById(R.id.tag4);
        signature.setText(Personal_Fragment.dataClass.getSignature());
        back = view.findViewById(R.id.data_back);
        back.setOnClickListener(this);
        over = view.findViewById(R.id.data_over);
        over.setOnClickListener(this);
        birthday = view.findViewById(R.id.data_birthday);
        birthday.setText(Personal_Fragment.dataClass.getBirthday());
        birthday.setOnClickListener(this);
    }

    public amend_data_Fragment(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Birthday() {
        showDateDialog(DateUtil.getDateForString(Personal_Fragment.dataClass.getBirthday()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(context);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {

                birthday.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));

            }

            @Override
            public void onCancel() {

            }
        })

                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.data_over) {
            if(username.getText().toString().equals("") || signature.getText().toString().equals("")) {
                Toast.makeText(context, "昵称或个性签名不能为空" , Toast.LENGTH_SHORT).show();
            }else {
                Personal_Fragment.dataClass.setUsername(username.getText().toString());
                Personal_Fragment.dataClass.setSignature(signature.getText().toString());
                Personal_Fragment.signature.setText(signature.getText().toString());
                Personal_Fragment.name.setText(username.getText().toString());
                Personal_Fragment.dataClass.setEmail(email.getText().toString());
                for (View view1 : Personal_Fragment.list2) {
                    TextView v = (TextView) view1;
                    v.setText(username.getText().toString());
                }
                if (radioGroup.getCheckedRadioButtonId() == R.id.boy) {
                    Personal_Fragment.dataClass.setSex((short) 1);
                    Personal_Fragment.personal_sex.setBackgroundResource(R.drawable.boy);
                } else {
                    Personal_Fragment.dataClass.setSex((short) 0);
                    Personal_Fragment.personal_sex.setBackgroundResource(R.drawable.girl);
                }
                Personal_Fragment.dataClass.setBirthday(birthday.getText().toString());

                Requests.Request(handler);
                //ReplaceFragment.showFragment(fragmentTransaction,this, new Personal_Fragment(context, getActivity()));
            }
        } else if (id == R.id.data_back) {
            getActivity().onBackPressed();
        } else if (id == R.id.data_birthday) {
            Birthday();
        }
    }


}
