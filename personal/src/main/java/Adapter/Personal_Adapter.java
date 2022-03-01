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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.personal.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import Fragments.Recycler_Fragment;
import Fragments.amend_data_Fragment;
import Fragments.amend_signature_Fragment;
import Fragments.amend_username_Fragment;
import IView.IBackgroundView;
import Presenters.BackgroundPresenter;
import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_Adapter extends RecyclerView.Adapter<Personal_Adapter.ViewHolder> implements IBackgroundView, View.OnClickListener {
    private Context context;
    private String tag;
    private FragmentActivity fm;
    private Activity activity;
    private ImageView imageView;
    private FragmentTransaction fragmentTransaction;
    private CircleImageView circleImageView;
    private TextView personal_username;
    private TextView personal_signature;
    private Button personal_edit;
    private Button personal_add;
    private Dialog dialog;
    private SelectItem mSelectItem;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;
    private BackgroundPresenter backgroundPresenter = new BackgroundPresenter(this);

    public Personal_Adapter(Context context, FragmentActivity fm, Activity activity, FragmentTransaction fragmentTransaction) {
        this.context = context;
        this.fm = fm;
        this.activity = activity;
        this.fragmentTransaction = fragmentTransaction;
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
                RecyclerView recyclerView = view1.findViewById(R.id.personal_tags);
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                TAG_Adapter tag_adapter = new TAG_Adapter();
                recyclerView.setAdapter(tag_adapter);
                circleImageView = view1.findViewById(R.id.personal_photo);
                circleImageView.setOnClickListener(this);
                personal_username = view1.findViewById(R.id.personal_username);
                personal_username.setOnClickListener(this);
                personal_signature = view1.findViewById(R.id.personal_signature);
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
                list.add(new Recycler_Fragment(context, R.layout.fragment_blogs, new Blogs_Adapter()));
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
        switch (view.getId()) {
            case R.id.personal_photo:
                tag = "0";
                dialog();
                break;
            case R.id.personal_background:
                tag = "1";
                dialog();
                Log.d("xxxxxx", String.valueOf(imageView.getRight()));
                Log.d("xxxxxx", String.valueOf(imageView.getBottom()));
                break;
            case R.id.tv_camera:
                Intent intent = backgroundPresenter.getModel().camera();
                activity.startActivityForResult(intent, SELECT_CAMER);
                if (null != mSelectItem) {
                    if(tag.equals("0")) {
                        mSelectItem.select(circleImageView, tag);
                    }else {
                        mSelectItem.select(imageView, tag);
                    }
                }
                dialog.dismiss();  //取消弹窗
                break;
            case R.id.tv_phone:
                intent = backgroundPresenter.getModel().photo();
                activity.startActivityForResult(intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                if (null != mSelectItem) {
                    if(tag.equals("0")) {
                        mSelectItem.select(circleImageView, tag);
                    }else {
                        mSelectItem.select(imageView, tag);
                    }
                }
                dialog.dismiss();  //取消弹窗
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
            case R.id.personal_signature:
                fragmentTransaction.replace(R.id.personal_frame, new amend_signature_Fragment(context)).commit();
                break;
            case R.id.personal_edit:
                fragmentTransaction.replace(R.id.personal_frame, new amend_data_Fragment(context)).commit();
                break;
            case R.id.personal_username:
                fragmentTransaction.replace(R.id.personal_frame, new amend_username_Fragment( context)).commit();
                break;
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
    }

    public void setSelectItem(SelectItem selectItem) {
        mSelectItem = selectItem;
    }

}
