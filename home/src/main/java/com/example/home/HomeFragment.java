package com.example.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.home.poisearch.PoiSearch_adapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements LocationSource,
        AMapLocationListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener{

    Context context;
    MapView mapView;
    ListView mapList;
    TextView textView;
    //    private AMapLocationClient mLocationClient;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocation aMapLocation;
    private Activity activity;
    private LatLng latlng;
    private String city = null;
    private AMap aMap;
    private Marker locationMarker; // 选择的点
    private String deepType = "";// poi搜索类型
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    private List<PoiItem> poiItems;// poi数据
    private PoiSearch_adapter adapter; //普通的 ListView 的 adapter，需根据需要自行编写
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption locationOption = null;


    public View onCreateView( LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapList = view.findViewById(R.id.map_list);
        textView = view.findViewById(R.id.local);
        MapsInitializer.updatePrivacyShow(context,true,true);
        MapsInitializer.updatePrivacyAgree(context,true);
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permission=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(),permission,1);
        }
        initLocation();
        //-------- 定位 Start ------
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(context.getApplicationContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.local_icon));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 235, 88, 41));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //开始搜索
        doSearchQuery();
        return view ;
    }
    private void initLocation(){
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnCameraChangeListener(this);
//            setUpMap();
//                doSearchQuery();
        }
        deepType = "010000";//这里以餐饮为例
    }
    //-------- 定位 Start ------

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
//            aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        query = new PoiSearch.Query("篮球馆", "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
//        getLatlon(city);
        if(latlng!=null) {
            LatLonPoint lp = new LatLonPoint(latlng.latitude, latlng.longitude);
            try {
                poiSearch = new PoiSearch(context.getApplicationContext(), query);
            } catch (AMapException e) {
                e.printStackTrace();
            }
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 3000));
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                textView.setText(aMapLocation.getCity());
                // 显示我的位置
                mListener.onLocationChanged(aMapLocation);
                //设置第一次焦点中心
                latlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14), 1000, null);
                city = aMapLocation.getProvince();
                doSearchQuery();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        mLocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        aMap.clear();
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latlng = cameraPosition.target;
        aMap.clear();
        MarkerOptions markerOption = new MarkerOptions().position(latlng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_localicon));
        //将数据添加到地图上
        aMap.addMarker(markerOption);
        doSearchQuery();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiItem数据，页数从数字0开始
                    Log.d("conttte", poiItems.get(1).toString());
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();
                    if (poiItems != null && poiItems.size() > 0) {
                        adapter = new PoiSearch_adapter(context.getApplicationContext(), poiItems);
                        mapList.setAdapter(adapter);
                        mapList.setOnItemClickListener(new HomeFragment.mOnItemClickListener());
                    }
                } else {
                    Log.d("wjg", "无结果");
                }
            }
        } else if (rCode == 27) {
            Log.d("errnet", "error_network");
        } else if (rCode == 32) {
            Log.d("errkey", "error_key");
        } else {
            Log.d("erroth", "error_other：" + rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    //-------- 定位 End ------

    @Override
    public void onResume() {
        super.onResume();
        mLocationClient.startLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
    }

    @Override
    public void onDestroy() {
        mLocationClient.onDestroy();
        super.onDestroy();
    }

    private class mOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            // 在Fragment1中创建Fragment2的实例

//            Bundle bundle = new Bundle();
////设置数据
//            fragment2.setArguments(bundle);
//调用上面的方法由 fragment1 跳转到 fragment2

            Intent intent = new Intent(getActivity(), ReserveActivity.class);
            intent.putExtra("court_name",poiItems.get(position).getTitle());
            intent.putExtra("court_latitude",poiItems.get(position).getLatLonPoint().getLatitude());
            intent.putExtra("court_longitude",poiItems.get(position).getLatLonPoint().getLongitude());
            startActivity(intent);

//            latlng = new LatLng(poiItems.get(position).getLatLonPoint().getLatitude(), poiItems.get(position).getLatLonPoint().getLongitude());
//            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17), 1000, null);
//            aMap.clear();
//            aMap.addMarker(new MarkerOptions().position(latlng));
//            Intent intent = new Intent();
//            Log.d("itemclick", String.valueOf(poiItems.get(position).getLatLonPoint().getLatitude()));
//            intent.putExtra(KEY_LAT, poiItems.get(position).getLatLonPoint().getLatitude());
//            intent.putExtra(KEY_LNG, poiItems.get(position).getLatLonPoint().getLongitude());
//            intent.putExtra(KEY_DES, poiItems.get(position).getTitle());
//            setResult(RESULT_OK, intent);
//            finish();
        }
    }

}
