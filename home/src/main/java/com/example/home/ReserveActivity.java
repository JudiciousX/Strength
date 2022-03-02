package com.example.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

public class ReserveActivity extends AppCompatActivity {

    ImageView tel;
    Button back;
    TextView name;
    MapView mapView;
    private AMap aMap;
    private LatLng latlng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        tel = findViewById(R.id.court_tel);
        back = findViewById(R.id.reserve_back);
        name = findViewById(R.id.court_name);
        mapView = findViewById(R.id.map);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13339478811"));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String court_name = getIntent().getStringExtra("court_name");
        name.setText(court_name);
        Double latitude = getIntent().getDoubleExtra("court_latitude",0.00);
        Double longitude = getIntent().getDoubleExtra("court_longitude",0.00);
        latlng = new LatLng(latitude,longitude);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initMap();
    }

    private void initMap() {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14), 1000, null);
        MarkerOptions markerOption = new MarkerOptions().position(latlng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_localicon));
        //将数据添加到地图上
        aMap.addMarker(markerOption);
    }
        @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }
}