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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.personal.R;

import Tool.Requests;

//修改个性签名
public class amend_signature_Fragment extends Fragment implements View.OnClickListener{
    Button back;
    TextView over;
    private Context context;
    private FragmentTransaction fragmentTransaction;
    private EditText signature_signature;
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

    public amend_signature_Fragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.amend_signature, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        signature_signature = view.findViewById(R.id.signature_signature);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        back = view.findViewById(R.id.signature_back);
        back.setOnClickListener(this);
        over = view.findViewById(R.id.signature_over);
        over.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.signature_over) {
            if(signature_signature.getText().toString().equals("")) {
                Toast.makeText(context, "个性签名不能为空", Toast.LENGTH_SHORT).show();
            }else {
                Personal_Fragment.dataClass.setSignature(signature_signature.getText().toString());
                Personal_Fragment.signature.setText(signature_signature.getText().toString());

                Requests.Request(handler);
            }

        } else if (id == R.id.signature_back) {
            getActivity().onBackPressed();
        }
    }



}
