package com.example.home;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.ViewHolder> {

    private List<Payitem> list;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View payView;
        ImageView payImg;
        TextView payText;

        public ViewHolder(View view) {
            super(view);
            payView = view;
            payImg = view.findViewById(R.id.pay_img);
            payText = view.findViewById(R.id.pay_text);
        }
    }

    public PayAdapter(List<Payitem> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Payitem payitem = new Payitem();
        holder.payImg.setImageResource(payitem.getPayimg());
        holder.payText.setText(payitem.getPaytext());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
