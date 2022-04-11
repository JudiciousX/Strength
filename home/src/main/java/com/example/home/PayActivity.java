package com.example.home;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {

    private List<Payitem> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initPay();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PayAdapter(list));
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
