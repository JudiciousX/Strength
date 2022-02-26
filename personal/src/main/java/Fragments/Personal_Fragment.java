package Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

import Adapter.Personal_Adapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_Fragment extends Fragment{
    private TextView textView;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Context context;
    private int height = 500;// 滑动开始变色的高
    private int overallXScroll = 0;
    private Activity activity;
    private ImageView imageView;
    private CircleImageView circleImageView;
    private String tag = new String();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        init(view);
        return view;
    }

    public Personal_Fragment(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    public void init(View view) {
        activity = getActivity();
        toolbar = view.findViewById(R.id.personal_toolbar);
        textView = view.findViewById(R.id.personal_title);
        recyclerView = view.findViewById(R.id.personal_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        Personal_Adapter adapter = new Personal_Adapter(context, this.getActivity(),activity );

        recyclerView.setAdapter(adapter);
        adapter.setSelectItem(new Personal_Adapter.SelectItem() {
            @Override
            public void select(View view, String sign) {
                tag = sign;
                if("0".equals(sign)) {
                    circleImageView = (CircleImageView) view;
                }else {
                    imageView = (ImageView) view;
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
}
