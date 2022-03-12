package com.example.court;

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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View attention;
        ImageView profile;
        TextView name;
        TextView address;
        TextView time;
        TextView information;

        public ViewHolder(View view){
            super(view);
            attention=view;
            profile=view.findViewById(R.id.dynamic_profile);
            name=view.findViewById(R.id.user_name);
            address=view.findViewById(R.id.court_address);
            time=view.findViewById(R.id.court_time_text);
            information=view.findViewById(R.id.court_information);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public CourtAdapter(FragmentActivity activity, List<Court_Context> list){this.list=list;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.court_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getAdapterPosition();
                Court_Context context=list.get(position);
//                Toast.makeText(view.getContext(), "you clicked View" + context.getName(), Toast.LENGTH_SHORT).show();

            }
        });
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
    }

        @Override
    public int getItemCount() {
        return list.size();
    }
}