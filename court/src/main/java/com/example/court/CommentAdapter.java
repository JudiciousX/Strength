package com.example.court;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{


    private List<Comment> list;
    private Context context;
//    //第一步：自定义一个回调接口来实现Click和LongClick事件
//    public interface OnItemClickListener {
//        void onItemClick(View v, int position);
//
//    }
//
//    public OnItemClickListener mOnItemClickListener;//第二步：声明自定义的接口
//
//    //第三步：定义方法并暴露给外面的调用者
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View attention;
        ImageView profile;
        TextView name;
        TextView address;
//        TextView time;
        TextView information;
//        ImageView rob;

        public ViewHolder(View view){
            super(view);
            attention=view;
            profile=view.findViewById(R.id.comment_profile);
            name=view.findViewById(R.id.comment_name);
            information=view.findViewById(R.id.comment_content);
        }

    }
    public CommentAdapter( List<Comment> list){

        this.list=list;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        Comment comment=list.get(position);
//        holder.profile.setImageResource(court_context.getProfile());
      holder.profile.setImageResource(R.drawable.court_profile);
        holder.name.setText(comment.getUsername());
        holder.information.setText(comment.getComment());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}