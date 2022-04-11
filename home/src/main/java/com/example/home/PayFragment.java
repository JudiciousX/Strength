package com.example.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class PayFragment extends BottomSheetDialogFragment {

    private List<Payitem> list= new ArrayList<>();
    private Context context;
    private View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getActivity()==null)
        return super.onCreateDialog(savedInstanceState);

        BottomSheetDialog dialog = new BottomSheetDialog(getActivity(),R.style.Theme_MaterialComponents_BottomSheetDialog);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        context = getContext();
        view = inflater.inflate(R.layout.activity_pay,container,false);
        initPay();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PayAdapter(list));
        return view;
    }
    private void initPay(){
        Payitem wechat =new Payitem();
        wechat.setPayimg(R.drawable.wechatpay);
        wechat.setPaytext("微信支付");
        list.add(wechat);
        Payitem ali = new Payitem();
        ali.setPayimg(R.drawable.alipay);
        ali.setPaytext("支付宝支付");
        list.add(ali);
    }
}
