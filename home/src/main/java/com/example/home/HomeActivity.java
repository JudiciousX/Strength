package com.example.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class HomeActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener{

    /**
     //     * 更新隐私合规状态,需要在初始化地图之前完成
     //     *
     //     * @param context:    上下文
     //     * @param isContains: 隐私权政策是否包含高德开平隐私权政策  true是包含
     //     * @param isShow:     隐私权政策是否弹窗展示告知用户 true是展示
     //     * @since 8.1.0
     //     */
    public static void updatePrivacyShow(Context context, boolean isContains, boolean isShow){

    }

    /**
     * 更新同意隐私状态,需要在初始化地图之前完成
     *
     * @param context: 上下文
     * @param isAgree: 隐私权政策是否取得用户同意  true是用户同意
     * @since 8.1.0
     */
    public static void updatePrivacyAgree(Context context, boolean isAgree) {

    }


    MapView mapView;
    ListView mapList;
    TextView textView;
//    private AMapLocationClient mLocationClient;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocation aMapLocation;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        List<String> permissionList=new ArrayList<>();
//        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if(!permissionList.isEmpty()){
//            String[] permission=permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(HomeActivity.this,permission,1);
//        }else {

//            startLocation();
        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);
            mapView = findViewById(R.id.map);
            mapView.onCreate(savedInstanceState);
            mapList = findViewById(R.id.map_list);
            textView = findViewById(R.id.local);
        initLocation();
//        }
    }
    private void initLocation(){
//        //初始化client
//        mLocationClient = new AMapLocationClient(this.getApplicationContext());
//        locationOption =getDefaultOption();
//        //设置定位参数
//        mLocationClient.setLocationOption(locationOption);
//        // 设置定位监听
//        mLocationClient.setLocationListener(locationListener);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnCameraChangeListener(this);
            setUpMap();
//                doSearchQuery();
        }
        deepType = "010000";//这里以餐饮为例
    }

//    private AMapLocationClientOption getDefaultOption(){
//        AMapLocationClientOption mOption = new AMapLocationClientOption();
//        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
//        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
//        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
//        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
//        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
//        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
//        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
//        return mOption;
//    }
//    /**
//     * 定位监听
//     */
//    AMapLocationListener locationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation location) {
//
//            if (null != location) {
//
//                StringBuffer sb = new StringBuffer();
//                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//                if(location.getErrorCode() == 0){
//                    sb.append("定位成功" + "\n");
//                    sb.append("市            : " + location.getCity() + "\n");
//                    sb.append("地    址    : " + location.getAddress() + "\n");
//                } else {
//                    //定位失败
//                    sb.append("定位失败" + "\n");
//                    sb.append("错误码:" + location.getErrorCode() + "\n");
//                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
//                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
//                }
//                //解析定位结果，
//                String result = sb.toString();
//                textView.setText(location.getCity());
//                Log.d("resultLocal",result);
//            } else {
//                Log.d("local","定位失败0");
//            }
//        }
//    };
//    /**
//     * 开始定位
//     *
//     * @since 2.8.0
//     * @author hongming.wang
//     *
//     */
//
//    private void startLocation(){
//        // 设置定位参数
//        locationClient.setLocationOption(locationOption);
//        // 启动定位
//        locationClient.startLocation();
//    }
    //-------- 定位 Start ------

    private void setUpMap() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());
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
        doSearchQuery();
    }
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
//            aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        query = new PoiSearch.Query("火锅", "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
//        getLatlon(city);
        if(latlng!=null) {
            LatLonPoint lp = new LatLonPoint(latlng.latitude, latlng.longitude);
            try {
                poiSearch = new PoiSearch(this, query);
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
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latlng = cameraPosition.target;
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(latlng));
        doSearchQuery();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    Log.d("conttte", poiItems.get(1).toString());
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();
                    if (poiItems != null && poiItems.size() > 0) {
                        adapter = new PoiSearch_adapter(this, poiItems);
                        mapList.setAdapter(adapter);
                        mapList.setOnItemClickListener(new mOnItemClickListener());
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
    protected void onResume() {
        super.onResume();
        mLocationClient.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.onDestroy();
        super.onDestroy();
    }

    private class mOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent intent = new Intent(HomeActivity.this,ReserveActivity.class);
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