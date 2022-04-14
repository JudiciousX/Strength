package PFragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photograph.Adapter.DiscernResultAdapter;
import com.example.photograph.R;
import com.example.photograph.model.GetDiscernResultResponse;
import com.example.photograph.model.GetTokenResponse;
import com.example.photograph.network.ApiService;
import com.example.photograph.network.ServiceGenerator;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class Photograph_Fragment extends Fragment implements View.OnClickListener{
    private ImageView photo_image;
    private TextView photo_text;
    private ImageView album_image;
    private TextView album_text;
    /**
     * 显示图片
     */
    private ImageView ivPicture;
    /**
     * 进度条
     */
    private ProgressBar pbLoading;

    private static final String TAG = "scout";
    /**
     * Api服务
     */
    private ApiService service;
    /**
     * 鉴权Toeken
     */
    public static String accessToken;

    /**
     * 底部弹窗
     */
    private BottomSheetDialog bottomSheetDialog;
    /**
     * 弹窗视图
     */
    private View bottomView;

    private RxPermissions rxPermissions;

    /**
     * 打开相册
     */
    private static final int OPEN_ALBUM_CODE = 100;

    private File outputImage;


    /**
     * 打开相机
     */
    private static final int TAKE_PHOTO_CODE = 101;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photograph, container, false);
        init(view);
        service = ServiceGenerator.createService(ApiService.class);
        requestApiGetToken();
        return view;
    }

    public void init(View view) {
        rxPermissions = new RxPermissions(getActivity());

        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);

        ivPicture = view.findViewById(R.id.picture);
        pbLoading = view.findViewById(R.id.pb_loading);

        Log.d("scout1", ivPicture.getId()+"");
        photo_image = view.findViewById(R.id.photo_image);
        photo_text = view.findViewById(R.id.photo_text);
        album_image = view.findViewById(R.id.album_image);
        album_text = view.findViewById(R.id.album_text);
        photo_image.setOnClickListener(this);
        photo_text.setOnClickListener(this);
        album_image.setOnClickListener(this);
        album_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.photo_image || id == R.id.photo_text) {
            //打开相机进行拍照
            //IdentifyWebPictures(view);
//            IdentifyAlbumPictures(view);
            IdentifyTakePhotoImage(view);
        }else {
            //打开相册选择照片
            IdentifyAlbumPictures(view);
        }
    }

    /**
     * 识别相册图片
     *
     * @param view
     */
    @SuppressLint("CheckResult")
    public void IdentifyAlbumPictures(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(grant -> {
                        if (grant) {
                            //获得权限
                            openAlbum();
                        } else {
                            Toast.makeText(getContext(), "未获取到权限", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            openAlbum();
        }
    }

    /**
     * 识别拍照图片
     *
     * @param view
     */

    @SuppressLint("CheckResult")
    public void IdentifyTakePhotoImage(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(
                    Manifest.permission.CAMERA)
                    .subscribe(grant -> {
                        if (grant) {
                            //获得权限
                            turnOnCamera();
                        } else {
                            showMsg("未获取到权限");
                        }
                    });
        } else {
            turnOnCamera();
        }
    }

    /**
     * 打开相机
     */
    private void turnOnCamera() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        //创建File对象
        outputImage = new File(getContext().getExternalCacheDir(), "takePhoto" + filename + ".jpg");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getContext(),
                    "com.example.photograph.fileProvider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //打开相机
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO_CODE);

    }

    //拍照 返回
    public String recognition() {
        //拍照返回
        String imagePath = outputImage.getAbsolutePath();
        return imagePath;
    }




    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    //识别网络图片
    public void IdentifyWebPictures(View view) {
        pbLoading.setVisibility(View.VISIBLE);
        if (accessToken == null) {
            showMsg("获取AccessToken到null");
            return;
        }
        String imgUrl = "https://lijiaxuan.oss-cn-shanghai.aliyuncs.com/head_sculpture/2022/04/11/a778598808324d808c394ebf93ba18e81649674061934.png";
        //显示图片
        Glide.with(this).load(imgUrl).into(ivPicture);
        showMsg("图像识别中");
        service.getDiscernResult(accessToken, imgUrl).enqueue(new Callback<GetDiscernResultResponse>() {
            @Override
            public void onResponse(Call<GetDiscernResultResponse> call, Response<GetDiscernResultResponse> response) {
                List<GetDiscernResultResponse.ResultBean> result = response.body() != null ? response.body().getResult() : null;
                if (result != null && result.size() > 0) {
                    //显示识别结果
                    showDiscernResult(result);
                } else {
                    pbLoading.setVisibility(View.GONE);
                    showMsg("未获得相应的识别结果");
                }
            }

            @Override
            public void onFailure(Call<GetDiscernResultResponse> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                Log.e(TAG, "图像识别失败，失败原因：" + t.toString());
            }

        });
    }

    /**
     * Toast提示
     * @param msg 内容
     */
    private void showMsg(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }


    /**
     * 显示识别的结果列表
     *
     * @param result
     */
    public void showDiscernResult(List<GetDiscernResultResponse.ResultBean> result) {
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(com.example.photograph.R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        RecyclerView rvResult = bottomView.findViewById(com.example.photograph.R.id.rv_result);
        DiscernResultAdapter adapter = new DiscernResultAdapter(com.example.photograph.R.layout.item_result_rv, result);
        rvResult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvResult.setAdapter(adapter);
        //隐藏加载
        pbLoading.setVisibility(View.GONE);
        //显示弹窗
        bottomSheetDialog.show();
    }


    public void ivPicture_1(String url) {
        Glide.with(this).load(url).into(ivPicture);

    }

    public void PbLoading_2() {
        pbLoading.setVisibility(View.GONE);
    }

    public void PbLoading_1() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 访问API获取接口
     */
    private void requestApiGetToken() {
        String grantType = "client_credentials";
        String apiKey = "TjPChftoEyBq7Nzm65KNerqr";
        String apiSecret = "eTph4jO95te6R3G2aecktGMbkieOv7rS";
        service.getToken(grantType, apiKey, apiSecret)
                .enqueue(new Callback<GetTokenResponse>() {
                    @Override
                    public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                        if (response.body() != null) {
                            //鉴权Token
                            accessToken = response.body().getAccess_token();
                            Log.d(TAG,accessToken);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                        Log.e(TAG, "获取Token失败，失败原因：" + t);
                        accessToken = null;
                    }

                });
    }


}
