package com.example.basketball;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.commlib.RetrofitBase;
import com.example.court.CourtFragment;
import com.example.home.HomeFragment;
import com.example.photograph.Tools.Base64Util;
import com.example.photograph.Tools.FileUtil;
import com.example.photograph.model.GetDiscernResultResponse;
import com.example.photograph.model.GetTokenResponse;
import com.example.photograph.network.ApiService;
import com.example.photograph.network.ServiceGenerator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import Fragments.Personal_Fragment;
import IClass.IClass2;
import IClass.IClass3;
import IRequest.NameRequest;
import PFragments.Photograph_Fragment;
import Tool.Requests;
import Tool.User;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


@Route(path = "/main/main")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private static Bitmap bitmap;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;
    private List<View> head;
    private ImageView imageView;
    private CircleImageView circleImageView;
    private Personal_Fragment fragment;
    private Context context = this;
    private Bitmap map;
    private Activity activity = this;
    private int size = 0;


    public static List<IClass2.ArticleContent> articleContent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj!=null){
                switch (msg.obj.toString()) {
                    case "200":
                        //?????????ImageView???
                        String tag = fragment.getSign();
                        if("0".equals(tag)) {
                            circleImageView = fragment.getCircleImageView();
                            Glide.with(context).load(map).apply(requestOptions).into(circleImageView);
                            Log.d("scout", head.toString());
                            for(View view : head) {
                                Log.d("xxxxxxxx", view.toString());
                                Glide.with(context).load(map).apply(requestOptions).into((CircleImageView) view);
                            }

                        }else {
                            imageView = fragment.getImageView();
                            Glide.with(context).load(map).into(imageView);
                        }
                        break;
                    case "500":
                        Toast.makeText(context, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    default :
                        fragment = new Personal_Fragment(context, activity , R.id.main_frame);
                        fragment.dataClass = (User) msg.obj;
                        Log.d("scout", msg.obj.toString());
                        head = fragment.getList1();
                        break;
                }
            }
        }
    };
    /**
     * ?????????????????????
     */
    public static final int PICTURE_CROPPING_CODE = 200;


    /**
     * Glide????????????????????????
     */
    private RequestOptions requestOptions = RequestOptions
            .circleCropTransform()//????????????
            .diskCacheStrategy(DiskCacheStrategy.NONE)//??????????????????
            .skipMemoryCache(true);//??????????????????


    /**
     * ????????????
     */
    private static final int OPEN_ALBUM_CODE = 100;

    private static final String TAG = "scout";
    /**
     * Api??????
     */
    private ApiService service;
    /**
     * ??????Toeken
     */
    public static String accessToken;

    /**
     * ????????????
     */
    private BottomSheetDialog bottomSheetDialog;
    /**
     * ????????????
     */
    private View bottomView;

    private RxPermissions rxPermissions;


    private Photograph_Fragment fragments;



    /**
     *
     * @param requestCode ????????????
     * @param resultCode  ????????????
     * @param data        ????????????
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            final Uri imageUri = Objects.requireNonNull(data).getData();
            String tag = fragment.getSign();
            if("0".equals(tag)) {
                pictureCropping2(imageUri);
            }else {
                //????????????
                pictureCropping(imageUri);
            }
        }else if(requestCode == SELECT_CAMER && resultCode == RESULT_OK)  {

            if(data.getData() == null) {
                Log.d("xxxxxx", "xxx");
                bitmap = (Bitmap) data.getExtras().get("data");
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                String tag = fragment.getSign();
                if("0".equals(tag)) {
                    pictureCropping2(uri);
                }else {
                    //????????????
                    pictureCropping(uri);
                }
            }else {
                Log.d("xxxxxx", "xx");
                final Uri imageUri = Objects.requireNonNull(data).getData();
                String tag = fragment.getSign();
                if("0".equals(tag)) {
                    pictureCropping2(imageUri);
                }else {
                    //????????????
                    pictureCropping(imageUri);
                }
            }
        }else if(requestCode == PICTURE_CROPPING_CODE && resultCode == RESULT_OK) {
            //??????????????????
            Bundle bundle = data.getExtras();
            Log.d("xxxxxx", "ooo");
            if (bundle != null) {
                Log.d("xxxxxx", "eee");
                //??????????????????????????????Bitmap???????????????????????????
                map = bundle.getParcelable("data");
                File file = saveImageToGallery(map);
                uploadFanganFile(file);
                deleteSuccess(this, file.getName());

            }
        }else if(resultCode == RESULT_OK) {
            fragments.PbLoading_1();
            if (requestCode == 65636) {

                //??????????????????
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                final Uri imageUri = Objects.requireNonNull(data).getData();
                Cursor cursor = getContentResolver().query(imageUri, filePathColumns, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
                //??????????????????
                String imagePath = cursor.getString(columnIndex);
                cursor.close();
                //??????
                localImageDiscern(imagePath);
            }else {
                String imagePath = fragments.recognition();
                localImageDiscern(imagePath);
            }
        }
    }

    //????????????
    public File saveImageToGallery(Bitmap bitmap) {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //????????????????????????ActivityCompat.requestPermissions?????????????????????????????????
            //??????????????????Activity?????? ????????????String?????? ??????????????????????????????????????? ????????????????????? ????????????????????????
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "android");
        if (!appDir.exists()) {
            // ??????????????? ?????????
            appDir.mkdirs();
        }
        //?????????CompressFormat.PNG/CompressFormat.JPEG??? ????????????.png/.jpeg
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // ??????bitmap?????????
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.d("Personal_TAG", "??????????????????" + e.toString());
            e.printStackTrace();
        } finally {
            if (bitmap!=null&&!bitmap.isRecycled()) {
                //???????????????????????????????????????OOM ???????????????Bitmap
                //bitmap.recycle();
                // ??????????????????
                System.gc();
            }
            //???????????????????????????
            return file;
        }
    }


    //????????????
    public static void deleteSuccess(Context context, String filePath) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + filePath + "'";
        //????????????
        mContentResolver.delete(uri, where, null);
    }

    //???????????????
    public void uploadFanganFile(File file) {
        String tag = fragment.getSign();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body;
        if("0".equals(tag)) {
            body = MultipartBody.Part.createFormData("headSculpture", file.getName(), requestFile);
        }else {
            body = MultipartBody.Part.createFormData("background", file.getName(), requestFile);
        }
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        NameRequest nameRequest = retrofit.create(NameRequest.class);

        Call<IClass3> call;
        if("0".equals(tag)) {
            call = nameRequest.upload2(RetrofitBase.mobileToken, RetrofitBase.uid, body);
        }else {
            call = nameRequest.upload1(RetrofitBase.mobileToken, RetrofitBase.uid, body);
        }
        call.enqueue(new Callback<IClass3>() {
            @Override
            public void onResponse(Call<IClass3> call, Response<IClass3> response) {
                Message message = new Message();
                message.obj = response.body().getCode();
                if(message.obj.equals("200")) {
                    String tag = fragment.getSign();
                    if("0".equals(tag)) {
                        Personal_Fragment.dataClass.setHead_sculpture(response.body().getData());
                    }else {
                        Personal_Fragment.dataClass.setBackground(response.body().getData());
                    }
                    Log.d("viper", response.body().getData());


                }
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<IClass3> call, Throwable t) {
                Log.d("Personal_TAG", "????????????" + t.toString());
            }
        });
    }

    //????????????
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // ?????????????????? EasyPermissions (???????????????????????????
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMsg("You agree the permission");
                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    //???????????? ??????
    //????????????
    private void pictureCropping(Uri uri) {
        // ????????????????????????????????????
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // ????????????crop=true?????????????????????Intent??????????????????VIEW?????????
        intent.putExtra("crop", "true");
        // aspectX aspectY ??????????????????
        intent.putExtra("aspectX", 1080);
        intent.putExtra("aspectY", 750);
        // outputX outputY ?????????????????????
        intent.putExtra("outputX", 432);
        intent.putExtra("outputY", 300);

        // ????????????????????????
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICTURE_CROPPING_CODE);

    }

    //???????????? ?????????
    private void pictureCropping2(Uri uri) {
        // ????????????????????????????????????
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // ????????????crop=true?????????????????????Intent??????????????????VIEW?????????
        intent.putExtra("crop", "true");
        // aspectX aspectY ??????????????????
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY ?????????????????????
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // ????????????????????????
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICTURE_CROPPING_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = ServiceGenerator.createService(ApiService.class);
        requestApiGetToken();
        bottomSheetDialog = new BottomSheetDialog(this);

        bottomNavigationView = findViewById(R.id.main_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        //???????????????
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        //????????????????????????
        Requests.Request1(handler);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //??????????????????
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.home:
                //??????
                fragmentTransaction.replace(R.id.main_frame, new HomeFragment()).commit();
                break;
            case R.id.ball_game:
                //??????
                fragmentTransaction.replace(R.id.main_frame, new CourtFragment()).commit();
                break;
            case R.id.personal:
                //??????
                //????????????
                fragmentTransaction.replace(R.id.main_frame,fragment).commit();
                break;
            case R.id.photograph:
                fragments = new Photograph_Fragment();
                fragmentTransaction.replace(R.id.main_frame, fragments).commit();
                break;
        }
        return true;
    }

    /**
     * ??????????????????
     */
    private void localImageDiscern(String imagePath) {

        try {
            if (Photograph_Fragment.accessToken == null) {
                showMsg("??????AccessToken???null");
                return;
            }
            //??????????????????????????????
            fragments.ivPicture_1(imagePath);
            //?????????????????????
            byte[] imgData = FileUtil.readFileByBytes(imagePath);
            //?????????Base64
            String imageBase64 = Base64Util.encode(imgData);
            //????????????
            ImageDiscern(Photograph_Fragment.accessToken, imageBase64, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * ??????????????????
     *
     * @param token       token
     * @param imageBase64 ??????Base64
     * @param imgUrl      ????????????Url
     */
    private void ImageDiscern(String token, String imageBase64, String imgUrl) {
        service.getDiscernResult1(token, imageBase64, imgUrl).enqueue(new Callback<GetDiscernResultResponse>() {
            @Override
            public void onResponse(Call<GetDiscernResultResponse> call, Response<GetDiscernResultResponse> response) {
                List<GetDiscernResultResponse.ResultBean> result = response.body() != null ? response.body().getResult() : null;
                if (result != null && result.size() > 0) {
                    //??????????????????
                    fragments.showDiscernResult(result);
                } else {
                    fragments.PbLoading_2();
                    showMsg("??????????????????????????????");
                }
            }

            @Override
            public void onFailure(Call<GetDiscernResultResponse> call, Throwable t) {
                fragments.PbLoading_2();
                Log.e(TAG, "????????????????????????????????????" + t);
            }

        });
    }

    /**
     * Toast??????
     * @param msg ??????
     */
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * ??????API????????????
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
                            //??????Token
                            accessToken = response.body().getAccess_token();
                            Log.d(TAG,accessToken);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                        Log.e(TAG, "??????Token????????????????????????" + t);
                        accessToken = null;
                    }

                });
    }



}