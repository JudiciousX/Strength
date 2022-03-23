package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.personal.R;

import Tool.Requests;

public class amend_username_Fragment extends Fragment implements View.OnClickListener {
    Button back;
    TextView over;
    private EditText name;
    private FragmentTransaction fragmentTransaction;
    private Context context;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.amend_username, container, false);
        init(view);

        return view;
    }

    public amend_username_Fragment( Context context) {
        this.context = context;
    }

    private void init(View view) {
        name = view.findViewById(R.id.username_name);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        back = view.findViewById(R.id.username_back);
        back.setOnClickListener(this);
        over = view.findViewById(R.id.username_over);
        over.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.username_over) {
            if(name.getText().toString().equals("")) {
                Toast.makeText(context, "昵称不能为空", Toast.LENGTH_SHORT).show();
            }else {
                Personal_Fragment.dataClass.setUsername(name.getText().toString());
                Personal_Fragment.name.setText(name.getText().toString());
                for (View view1 : Personal_Fragment.list2) {
                    TextView v = (TextView) view1;
                    v.setText(name.getText().toString());
                }
                Requests.Request(handler);
            }
        } else if (id == R.id.username_back) {
            getActivity().onBackPressed();
        }
    }

}
