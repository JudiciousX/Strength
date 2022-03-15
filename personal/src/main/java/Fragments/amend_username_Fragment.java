package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import com.example.commlib.RetrofitBase;
import com.example.personal.R;
import com.google.gson.Gson;

import IClass.IClass;
import IRequest.NameRequest;
import Tool.Requests;
import Tool.User;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
            Personal_Fragment.dataClass.setUsername(name.getText().toString());
            Personal_Fragment.name.setText(name.getText().toString());
            for (View view1 : Personal_Fragment.list2) {
                TextView v = (TextView) view1;
                v.setText(name.getText().toString());
            }
            Requests.Request(handler);
        } else if (id == R.id.username_back) {
            getActivity().onBackPressed();
        }
    }

    public void Request() {
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        NameRequest nameRequest = retrofit.create(NameRequest.class);
        //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(Personal_Fragment.dataClass));
        Gson gson = new Gson();
        String userInfoBean = gson.toJson(Personal_Fragment.dataClass, User.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfoBean);
        Log.d("xxxxx", userInfoBean);
        nameRequest.getName(body).enqueue(new Callback<IClass>() {
            @Override
            public void onResponse(Call<IClass> call, Response<IClass> response) {
                Message message = new Message();
                message.obj = response.body().getCode();
                Log.d("xxxxxx", response.body().getCode());
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<IClass> call, Throwable t) {
                Log.d("TAG", "请求失败");
            }
        });
    }

}
