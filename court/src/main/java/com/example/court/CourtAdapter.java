package com.example.court;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourtAdapter extends RecyclerView.Adapter<CourtAdapter.ViewHolder>{


    private List<Court_Context> list;
    private Context context;
    //第一步：自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

    }

    public OnItemClickListener mOnItemClickListener;//第二步：声明自定义的接口

    //第三步：定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View attention;
        ImageView profile;
        TextView name;
        TextView address;
        TextView time;
        TextView information;
        ImageView rob;

        public ViewHolder(View view){
            super(view);
            attention=view;
            profile=view.findViewById(R.id.dynamic_profile);
            name=view.findViewById(R.id.user_name);
            address=view.findViewById(R.id.court_address);
            time=view.findViewById(R.id.court_time_text);
            information=view.findViewById(R.id.court_information);
            rob = view.findViewById(R.id.court_rob);

            rob.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
    public CourtAdapter( List<Court_Context> list){

        this.list=list;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.court_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourtAdapter.ViewHolder holder, int position) {
        Court_Context court_context=list.get(position);
        holder.profile.setImageResource(court_context.getProfile());
        holder.name.setText(court_context.getName());
        holder.address.setText(court_context.getAddress());
        holder.information.setText(court_context.getInformation());
        holder.time.setText(court_context.getTime());
        holder.rob.setTag(position);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}