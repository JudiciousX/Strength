package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.personal.R;

import java.util.ArrayList;
import java.util.List;

import Fragments.Personal_Fragment;
import Tool.Requests;
import de.hdodenhof.circleimageview.CircleImageView;

public class Blogs_Adapter extends RecyclerView.Adapter<Blogs_Adapter.ViewHolder> {
    private CircleImageView head;
    private TextView user;
    private TextView time;
    private TextView description;
    private List<View> head1 = new ArrayList<>();
    private List<View> user1 = new ArrayList<>();
    private List<View> time1 = new ArrayList<>();
    private Context context;
    /**
     * Glide请求图片选项配置
     */
    private RequestOptions requestOptions = RequestOptions
            .circleCropTransform()//圆形剪裁
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    public Blogs_Adapter(Context context) {
        this.context = context;
    }


    @Override
    public Blogs_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_blogs, parent, false);
        head = view.findViewById(R.id.personal_head);
        user = view.findViewById(R.id.personal_user);
        time = view.findViewById(R.id.personal_time);
        description = view.findViewById(R.id.personal_description);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Blogs_Adapter.ViewHolder holder, int position) {
        time.setText(Personal_Fragment.ArticleContentList.get(position).getTime());
        description.setText(Personal_Fragment.ArticleContentList.get(position).getContent());
        user.setText(Personal_Fragment.dataClass.getUsername());
        Glide.with(context).load(Personal_Fragment.dataClass.getHead_sculpture()).apply(requestOptions).into(head);
        Personal_Fragment.list1.add(head);
        user1.add(user);
        time1.add(time);
    }

    @Override
    public int getItemCount() {
        return Personal_Fragment.ArticleContentList.size();
    }

    public List<View> getUser1() {
        return user1;
    }
}
