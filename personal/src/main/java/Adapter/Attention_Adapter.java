package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.personal.R;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

import Fragments.Personal_Fragment;
import Tool.User;
import Tool.User1;
import de.hdodenhof.circleimageview.CircleImageView;

public class Attention_Adapter extends RecyclerView.Adapter<Attention_Adapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private ViewHolder holder;

    /**
     * Glide请求图片选项配置
     */
    private RequestOptions requestOptions = RequestOptions
            .circleCropTransform()//圆形剪裁
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存


    public Attention_Adapter (Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        int position = holder.getAdapterPosition();
        String uid = Personal_Adapter.list.get(position).getUid();
        //跳转到所关注人的主页

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        private ViewStub viewStub;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.attention_head);
            viewStub = itemView.findViewById(R.id.emptyView);
            textView = itemView.findViewById(R.id.attention_username);
        }
    }


    @NonNull
    @Override
    public Attention_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_attention, parent, false);
        holder = new ViewHolder(view);
        holder.textView.setOnClickListener(this);
        holder.circleImageView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Attention_Adapter.ViewHolder holder, int position) {
        holder.textView.setText(Personal_Adapter.list.get(position).getUsername());
        Glide.with(context).load(Personal_Adapter.list.get(position).getHeadSculpture()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return Personal_Adapter.list.size();
    }
}
