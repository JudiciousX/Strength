package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.personal.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import Fragments.Collect_Fragment;
import Fragments.Recycler_Fragment;

public class Personal_Adapter extends RecyclerView.Adapter<Personal_Adapter.ViewHolder> {
    private Context context;
    private FragmentActivity fm;

    public Personal_Adapter(Context context, FragmentActivity fm) {
        this.context = context;
        this.fm = fm;
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
                return new ViewHolder(view);
            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basic_information, parent, false);
                RecyclerView recyclerView = view1.findViewById(R.id.personal_tags);
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                TAG_Adapter tag_adapter = new TAG_Adapter();
                recyclerView.setAdapter(tag_adapter);
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


}
