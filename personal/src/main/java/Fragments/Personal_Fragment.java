package Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.Personal_Adapter;
import Tool.DataClass;
import Tool.ReplaceFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_Fragment extends Fragment{
    public static DataClass dataClass;
    public static final String TAG = "RightFragment";
    public static List<String> tags;
    private TextView textView;
    private Toolbar toolbar;
    private Button more;
    private RecyclerView recyclerView;
    private Context context;
    private int height = 500;// 滑动开始变色的高
    private int overallXScroll = 0;
    private Activity activity;
    private ImageView imageView;
    public static TextView name;
    private CircleImageView circleImageView;
    private String tag = new String();
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    public static List<View> list1;
    public static List<View> list2;
    public static String username;
    public static TextView signature;
    public static CircleImageView personal_sex;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        username = "";
        Log.d(TAG, "onCreateView");
        init(view);
        return view;
    }

    public Personal_Fragment(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }


    public void init(View view) {
        dataClass = new DataClass();
        tags = new ArrayList<>();
        tags.add("  篮球  ");
        tags.add("  足球  ");
        tags.add("  乒乓球  ");
        dataClass.setLabel(tags);
        dataClass.setUsername("摩西摩西");
        dataClass.setSex((short) 1);
        dataClass.setPhone_numbers("123456");
        Date date = new Date(20020212);
        dataClass.setBirthday("2002-01-03");
        dataClass.setSignature("没想好");
        fragment = this;
        more = view.findViewById(R.id.personal_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                ReplaceFragment.showFragment(fragmentTransaction, fragment,  new amend_data_Fragment(context));
            }
        });
        activity = getActivity();
        toolbar = view.findViewById(R.id.personal_toolbar);
        textView = view.findViewById(R.id.personal_title);
        recyclerView = view.findViewById(R.id.personal_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        Personal_Adapter adapter = new Personal_Adapter(this, context, this.getActivity(),activity);

        recyclerView.setAdapter(adapter);
        adapter.setSelectItem(new Personal_Adapter.SelectItem() {
            @Override
            public void select(View view, String sign) {
                tag = sign;
                if("0".equals(sign)) {
                    circleImageView = (CircleImageView) view;
                }
                if("1".equals(sign)) {
                    imageView = (ImageView) view;
                }
                if("2".equals(sign)) {
                    name = (TextView) view;
                }
                if("3".equals(sign)) {
                    signature = (TextView) view;
                }
                if("4".equals(sign)) {
                    personal_sex = (CircleImageView) view;
                }
            }

            @Override
            public void getList(List<View> list, String sign) {
                if("0".equals(sign)) {
                    list1 = list;
                }else if("1".equals(sign)) {
                    list2 = list;
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                title(dy);
            }

        });

    }

    //实现滑动渐变出现标题栏
    public void title(int dy) {
        overallXScroll = overallXScroll + dy;// 累加y值 解决滑动一半y值为0
        if (overallXScroll <= toolbar.getHeight()) {  //该区域时，设置透明度为0
            textView.setTextColor(Color.argb((int) 0, 0, 0, 0));
            toolbar.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
        } else if (overallXScroll > toolbar.getHeight()  && overallXScroll <= height) { //确定一个渐变区域，背景颜色透明度渐变
            //设置渐变比例
            float scale = (float) overallXScroll / height;
            float alpha = (255 * scale);
            toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            textView.setTextColor(Color.argb((int) alpha, 0, 0, 0));
        } else {//超过渐变区域，透明度都是满的
            textView.setTextColor(Color.argb((int) 255, 0, 0, 0));
            toolbar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public CircleImageView getCircleImageView() {
        return circleImageView;
    }

    public String getSign() {
        return tag;
    }

    public List<View> getList1() {
        return list1;
    }

}
