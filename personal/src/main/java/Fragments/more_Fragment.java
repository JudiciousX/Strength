package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.personal.R;

public class more_Fragment extends Fragment {
    private TextView textView;
    private Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        textView = view.findViewById(R.id.more_exit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //退出登录并返回登录页面
                ARouter.init(getActivity().getApplication()); // 尽可能早，推荐在Application中初始化ARouter
                ARouter.getInstance().inject(this);
                ARouter.getInstance().build("/login/login").navigation();
            }
        });
        back = view.findViewById(R.id.more_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
