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

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
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

import Search.SearchActivity;

public class HomeFragment extends Fragment implements LocationSource,
        AMapLocationListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener{

    Context context;
    MapView mapView;
    ListView mapList;
    TextView textView;
    CardView search;
    //    private AMapLocationClient mLocationClient;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocation aMapLocation;
    private Activity activity;
    private LatLng latlng;
    private String city = null;
    private AMap aMap;
    private Marker locationMarker; // ????????????
    private String deepType = "";// poi????????????
    private PoiSearch.Query query;// Poi???????????????
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi???????????????
    private List<PoiItem> poiItems;// poi??????
    private PoiSearch_adapter adapter; //????????? ListView ??? adapter??????????????????????????????
    //??????AMapLocationClient?????????
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption locationOption = null;


    public View onCreateView( LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapList = view.findViewById(R.id.map_list);
        textView = view.findViewById(R.id.local);
        search = view.findViewById(R.id.search_title);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });










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
        //-------- ?????? Start ------
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(context.getApplicationContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //??????????????????
            mLocationClient.setLocationListener(this);
            //??????????????????????????????
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //??????????????????
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
        // ??????????????????????????????
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.local_icon));// ????????????????????????
        myLocationStyle.strokeColor(Color.BLACK);// ???????????????????????????
        myLocationStyle.radiusFillColor(Color.argb(100, 235, 88, 41));// ???????????????????????????
        myLocationStyle.strokeWidth(1.0f);// ???????????????????????????
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// ??????????????????
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// ????????????????????????????????????
        aMap.setMyLocationEnabled(true);// ?????????true??????????????????????????????????????????false??????????????????????????????????????????????????????false
        //????????????
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
        deepType = "010000";//?????????????????????
    }
    //-------- ?????? Start ------

    /**
     * ????????????poi??????
     */
    protected void doSearchQuery() {
//            aMap.setOnMapClickListener(null);// ??????poi????????????????????????????????????
        query = new PoiSearch.Query("?????????", "", city);// ????????????????????????????????????????????????????????????poi????????????????????????????????????poi??????????????????????????????????????????
        query.setPageSize(20);// ?????????????????????????????????poiitem
        query.setPageNum(1);// ??????????????????
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
            poiSearch.searchPOIAsyn();// ????????????
        }
    }



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                textView.setText(aMapLocation.getCity());
                // ??????????????????
                mListener.onLocationChanged(aMapLocation);
                //???????????????????????????
                latlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14), 1000, null);
//                city = aMapLocation.getProvince();
//                MarkerOptions markerOption = new MarkerOptions().position(latlng)
//                        .draggable(false)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_localicon));
//                //???????????????????????????
//                aMap.addMarker(markerOption);
                doSearchQuery();
            } else {
                String errText = "????????????," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
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
//        MarkerOptions markerOption = new MarkerOptions().position(latlng)
//                .draggable(false)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_localicon));
//        //???????????????????????????
//        aMap.addMarker(markerOption);
        doSearchQuery();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// ??????poi?????????
                if (result.getQuery().equals(query)) {// ??????????????????
                    poiResult = result;
                    poiItems = poiResult.getPois();// ??????????????????poiItem????????????????????????0??????
                    for(PoiItem poii:poiItems){
                        double latitude,longitude;
                        latitude=poii.getLatLonPoint().getLatitude();
                        longitude=poii.getLatLonPoint().getLongitude();
                        LatLng lng = new LatLng(latitude,longitude);
                        MarkerOptions markerOption = new MarkerOptions().position(lng)
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_localicon));
                        //???????????????????????????
                        aMap.addMarker(markerOption);
                    }
                    Log.d("conttte", poiItems.get(1).toString());
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();
                    if (poiItems != null && poiItems.size() > 0) {
                        adapter = new PoiSearch_adapter(context.getApplicationContext(), poiItems);
                        mapList.setAdapter(adapter);
                        mapList.setOnItemClickListener(new HomeFragment.mOnItemClickListener());
                    }
                } else {
                    Log.d("wjg", "?????????");
                }
            }
        } else if (rCode == 27) {
            Log.d("errnet", "error_network");
        } else if (rCode == 32) {
            Log.d("errkey", "error_key");
        } else {
            Log.d("erroth", "error_other???" + rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    //-------- ?????? End ------

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


            // ???Fragment1?????????Fragment2?????????

//            Bundle bundle = new Bundle();
////????????????
//            fragment2.setArguments(bundle);
//???????????????????????? fragment1 ????????? fragment2

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
