package Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.personal.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import Fragments.Personal_Fragment;
import Fragments.Recycler_Fragment;
import Fragments.amend_data_Fragment;
import Fragments.amend_signature_Fragment;
import Fragments.amend_username_Fragment;
import IView.IBackgroundView;
import Presenters.BackgroundPresenter;
import Tool.ReplaceFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_Adapter extends RecyclerView.Adapter<Personal_Adapter.ViewHolder> implements IBackgroundView, View.OnClickListener {
    private Context context;
    private List<View> head1;
    private List<View> user1;
    private List<View> time1;
    private List<String> tags;
    private String tag;
    private Blogs_Adapter adapter1 = new Blogs_Adapter();
    private FragmentActivity fm;
    private Activity activity;
    private ImageView imageView;
    private FragmentTransaction fragmentTransaction;
    private CircleImageView circleImageView;
    private TextView personal_username;
    private RecyclerView recyclerView;
    private TextView personal_signature;
    private Button personal_edit;
    private Button personal_add;
    private CircleImageView personal_sex;
    private Dialog dialog;
    private TextView personal_phone;
    private SelectItem mSelectItem;
    private Fragment fragment;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;
    private BackgroundPresenter backgroundPresenter = new BackgroundPresenter(this);

    public Personal_Adapter(Fragment fragment, Context context, FragmentActivity fm, Activity activity) {
        this.context = context;
        this.fragment = fragment;
        this.fm = fm;
        this.activity = activity;
        head1 = new ArrayList<>();
        user1 = new ArrayList<>();
        time1 = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background, parent, false);
                imageView = view.findViewById(R.id.personal_background);
                imageView.setOnClickListener(this);
                return new ViewHolder(view);
            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basic_information, parent, false);
                recyclerView = view1.findViewById(R.id.personal_tags);
                //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                //recyclerView.setLayoutManager(layoutManager);
                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                recyclerView.setLayoutManager(layoutManager);

                TAG_Adapter tag_adapter = new TAG_Adapter();
                recyclerView.setAdapter(tag_adapter);
                personal_sex = view1.findViewById(R.id.personal_sex);
                if(Personal_Fragment.dataClass.getSex() == 1) {
                    personal_sex.setBackgroundResource(R.drawable.boy);
                }else {
                    personal_sex.setBackgroundResource(R.drawable.girl);
                }
                personal_phone = view1.findViewById(R.id.personal_phone);
                personal_phone.setText("ID:" + Personal_Fragment.dataClass.getPhoneNumbers());
                circleImageView = view1.findViewById(R.id.personal_photo);
                circleImageView.setOnClickListener(this);
                personal_username = view1.findViewById(R.id.personal_username);
                personal_username.setText(Personal_Fragment.dataClass.getUsername());
                personal_username.setOnClickListener(this);
                personal_signature = view1.findViewById(R.id.personal_signature);
                personal_signature.setText(Personal_Fragment.dataClass.getSignature());
                personal_signature.setOnClickListener(this);
                personal_edit = view1.findViewById(R.id.personal_edit);
                personal_edit.setOnClickListener(this);
                personal_add = view1.findViewById(R.id.personal_add);
                personal_add.setOnClickListener(this);
                return new ViewHolder(view1);
            case 2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false);
                TabLayout tabLayout = view2.findViewById(R.id.personal_tabLayout);
                ViewPager2 viewPager2 = view2.findViewById(R.id.personal_vp2);
                //设置滑动方向
                viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

                //设置默认页面
                viewPager2.setCurrentItem(1);
                List<Fragment> list = new ArrayList<>();
                List<String> titles = new ArrayList<>();

                list.add(new Recycler_Fragment(context, R.layout.fragment_blogs, adapter1));
                list.add(new Recycler_Fragment(context, R.layout.fragment_collect, new Collect_Adapter()));
                titles.add("博客");
                titles.add("收藏");
                Tab_Adapter adapter = new Tab_Adapter(fm, list);
                viewPager2.setAdapter(adapter);

                //关联TabLayout 添加attach()
                new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(TabLayout.Tab tab, int position) {
                        tab.setText(titles.get(position));
                    }
                }).attach();

                return new ViewHolder(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(Personal_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    @Override
    public void dialog() {
        //选择对话框
        dialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_content_normal, null);
        //获取Dialog的监听
        TextView tv_camera = (TextView) contentView.findViewById(R.id.tv_camera);
        TextView tv_phone = (TextView) contentView.findViewById(R.id.tv_phone);
        TextView tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel);

        tv_camera.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        //弹窗位置
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        //弹窗样式
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        //显示弹窗
        dialog.show();
    }

    @Override
    public void blogs() {

    }

    @Override
    public void arena() {

    }

    @Override
    public void modified() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.personal_photo) {
            head1 = adapter1.getHead1();
            tag = "0";
            dialog();
        } else if (id == R.id.personal_background) {
            tag = "1";
            dialog();
            Log.d("xxxxxx", String.valueOf(imageView.getRight()));
            Log.d("xxxxxx", String.valueOf(imageView.getBottom()));
        } else if (id == R.id.tv_camera) {
            Intent intent = backgroundPresenter.getModel().camera();
            activity.startActivityForResult(intent, SELECT_CAMER);
            if (null != mSelectItem) {
                if (tag.equals("0")) {
                    mSelectItem.select(circleImageView, tag);
                    mSelectItem.getList(head1, "0");
                } else {
                    mSelectItem.select(imageView, tag);
                }
            }
            dialog.dismiss();  //取消弹窗
        } else if (id == R.id.tv_phone) {
            Intent intent;
            intent = backgroundPresenter.getModel().photo();
            activity.startActivityForResult(intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
            if (null != mSelectItem) {
                if (tag.equals("0")) {
                    mSelectItem.select(circleImageView, tag);
                    mSelectItem.getList(head1, "0");
                } else {
                    mSelectItem.select(imageView, tag);
                }
            }
            dialog.dismiss();  //取消弹窗
        } else if (id == R.id.tv_cancel) {
            dialog.dismiss();
        } else if (id == R.id.personal_signature) {
            mSelectItem.select(personal_signature, "3");
            fragmentTransaction = fm.getSupportFragmentManager().beginTransaction();
            ReplaceFragment.showFragment(fragmentTransaction, fragment, new amend_signature_Fragment(context));
        } else if (id == R.id.personal_edit) {
            mSelectItem.select(personal_sex, "4");
            mSelectItem.select(personal_username, "2");
            mSelectItem.getList(adapter1.getUser1(), "1");
            mSelectItem.select(personal_signature, "3");
            fragmentTransaction = fm.getSupportFragmentManager().beginTransaction();
            ReplaceFragment.showFragment(fragmentTransaction, fragment, new amend_data_Fragment(context));
        } else if (id == R.id.personal_username) {
            mSelectItem.select(personal_username, "2");
            mSelectItem.getList(adapter1.getUser1(), "1");
            fragmentTransaction = fm.getSupportFragmentManager().beginTransaction();
            ReplaceFragment.showFragment(fragmentTransaction, fragment, new amend_username_Fragment(context));
        } else if (id == R.id.personal_add) {//选择对话框
            dialog = new Dialog(context, R.style.BottomDialog);
            View v = LayoutInflater.from(context).inflate(R.layout.tags_dialog, null);
            //获取控件
            final TextView tag1 = v.findViewById(R.id.tag_tag1);
            final TextView tag2 = v.findViewById(R.id.tag_tag2);
            final TextView tag3 = v.findViewById(R.id.tag_tag3);
            final TextView tag4 = v.findViewById(R.id.tag_tag4);
            final TextView tag5 = v.findViewById(R.id.tag_tag5);
            final TextView tag6 = v.findViewById(R.id.tag_tag6);
            final TextView tag7 = v.findViewById(R.id.tag_tag7);
            final TextView tag8 = v.findViewById(R.id.tag_tag8);
            final TextView tag9 = v.findViewById(R.id.tag_tag9);
            final TextView tag10 = v.findViewById(R.id.tag_tag10);
            final TextView tag11 = v.findViewById(R.id.tag_tag11);
            final TextView tag12 = v.findViewById(R.id.tag_tag12);
            final TextView tag13 = v.findViewById(R.id.tag_tag13);
            final TextView tag14 = v.findViewById(R.id.tag_tag14);
            final TextView tag15 = v.findViewById(R.id.tag_tag15);
            final Button over = v.findViewById(R.id.tag_button);
            showTag(tag1);
            showTag(tag2);
            showTag(tag3);
            showTag(tag4);
            showTag(tag5);
            showTag(tag6);
            showTag(tag7);
            showTag(tag8);
            showTag(tag9);
            showTag(tag10);
            showTag(tag11);
            showTag(tag12);
            showTag(tag13);
            showTag(tag14);
            showTag(tag15);
            over.setOnClickListener(this);
            tag1.setOnClickListener(this);
            tag2.setOnClickListener(this);
            tag3.setOnClickListener(this);
            tag4.setOnClickListener(this);
            tag5.setOnClickListener(this);
            tag6.setOnClickListener(this);
            tag7.setOnClickListener(this);
            tag8.setOnClickListener(this);
            tag9.setOnClickListener(this);
            tag10.setOnClickListener(this);
            tag11.setOnClickListener(this);
            tag12.setOnClickListener(this);
            tag13.setOnClickListener(this);
            tag14.setOnClickListener(this);
            tag15.setOnClickListener(this);
            dialog.setContentView(v);
            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
            v.setLayoutParams(layoutParams);
            //弹窗位置
            dialog.getWindow().setGravity(Gravity.BOTTOM);

            //显示弹窗
            dialog.show();
        } else if (id == R.id.tag_button) {
            TAG_Adapter tag_adapter = new TAG_Adapter();
            recyclerView.setAdapter(tag_adapter);
            Toast.makeText(context, "ooo", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            //修改兴趣标签
        } else {
            if (view.getBackground().getConstantState().equals(context.getResources().getDrawable(R.drawable.backggg).getConstantState())) {
                view.setBackgroundResource(R.drawable.backgg);
                TextView v0 = (TextView) view;
                if (!Personal_Fragment.tags.contains(v0.getText())) {
                    Personal_Fragment.tags.add(v0.getText().toString());
                }

            } else {
                TextView v0 = (TextView) view;
                view.setBackgroundResource(R.drawable.backggg);
                if (Personal_Fragment.tags.contains(v0.getText().toString())) {
                    Personal_Fragment.tags.remove(v0.getText().toString());
                }
            }
            for (String str : Personal_Fragment.tags) {
                Log.d("xxxxxxx", str);
            }
        }
    }
    /**
     * 在活动中实现的接口
     */
    public interface SelectItem {
        /**
         * 在活动中定义的方法
         * @param view view对象
         */
        void select(View view, String sign);
        void getList(List<View> list, String sign);
    }

    public void setSelectItem(SelectItem selectItem) {
        mSelectItem = selectItem;
    }

    public void showTag(TextView textView) {
        if(Personal_Fragment.tags.contains(textView.getText())) {
            textView.setBackgroundResource(R.drawable.backgg);
        }
    }


}
