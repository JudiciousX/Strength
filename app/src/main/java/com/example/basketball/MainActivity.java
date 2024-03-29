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
                        //设置到ImageView上
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
                        Toast.makeText(context, "服务器异常，上传失败", Toast.LENGTH_SHORT).show();
                        break;
                    default :
                        fragment = new Personal_Fragment(context, activity , R.id.main_frame);
//                        fragment.dataClass = (User) msg.obj;
                        fragment.dataClass = new User("13389106597", "13389106597", "JudiciousX", "减肥也要多吃饭", "[  足球  ,   台球  ,   网球  ]", (short) 0, "12345@qq.com", "2023-6-9", "http://m.qpic.cn/psc?/V10JG2ek3hqAnT/ruAMsa53pVQWN7FLK88i5r.79Xn0Bn3vTtBqRcD8czKwdyVjx6j0Pge0wNn1HxHIsFoUF4dIVxbQcuKrQp2LWvuKo29FXjs8WuAMiK6sBO0!/b&bo=sAR3A7AEdwMHFzY!&rf=viewer_4", "http://phototj.photo.store.qq.com/psc?/V506PzcE3M9HJv06WJzs3aG2Lm3l2E9J/ruAMsa53pVQWN7FLK88i5k..BW0E3Wx96XSpdfLwIwQZHaXwdocLZjEhUBoeAkJndqAGNe3j5zdn*RlkS2b2Y3Syz2LKPqCcKCxzEgKVrKE!/b&bo=YAlABmAJQAYDl7I!&rf=viewer_4");
//                        Log.d("scout", msg.obj.toString());
                        head = fragment.getList1();
                        break;
                }
            }
        }
    };
    /**
     * 图片剪裁请求码
     */
    public static final int PICTURE_CROPPING_CODE = 200;


    /**
     * Glide请求图片选项配置
     */
    private RequestOptions requestOptions = RequestOptions
            .circleCropTransform()//圆形剪裁
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存


    /**
     * 打开相册
     */
    private static final int OPEN_ALBUM_CODE = 100;

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


    private Photograph_Fragment fragments;



    /**
     *
     * @param requestCode 请求代码
     * @param resultCode  结果代码
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            final Uri imageUri = Objects.requireNonNull(data).getData();
//            final Uri imageUri = Uri.parse("http://phototj.photo.store.qq.com/psc?/V506PzcE3M9HJv06WJzs3aG2Lm3l2E9J/ruAMsa53pVQWN7FLK88i5on7m.7Eg*cakseAvA4U3nvfCtBQb13*qx9LC8WOXpucZ7b.U7creo*ZoZKj1yJ7TGoLYFl96FILKAxDTeZvzIg!/b&bo=7wk*Bu8JPwYDByI!&rf=viewer_4");
            String tag = fragment.getSign();
            if("0".equals(tag)) {
                pictureCropping2(imageUri);
            }else {
                //图片剪裁
                pictureCropping(imageUri);
            }
        }else if(requestCode == SELECT_CAMER && resultCode == RESULT_OK)  {

            if(data.getData() == null) {
                Log.d("xxxxxx", "xxx");
                bitmap = (Bitmap) data.getExtras().get("data");
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
//                Uri uri = Uri.parse("http://phototj.photo.store.qq.com/psc?/V506PzcE3M9HJv06WJzs3aG2Lm3l2E9J/ruAMsa53pVQWN7FLK88i5on7m.7Eg*cakseAvA4U3nvfCtBQb13*qx9LC8WOXpucZ7b.U7creo*ZoZKj1yJ7TGoLYFl96FILKAxDTeZvzIg!/b&bo=7wk*Bu8JPwYDByI!&rf=viewer_4");
                String tag = fragment.getSign();
                if("0".equals(tag)) {
                    pictureCropping2(uri);
                }else {
                    //图片剪裁
                    pictureCropping(uri);
                }
            }else {
                Log.d("xxxxxx", "xx");
                final Uri imageUri = Objects.requireNonNull(data).getData();
//                final Uri imageUri = Uri.parse("http://phototj.photo.store.qq.com/psc?/V506PzcE3M9HJv06WJzs3aG2Lm3l2E9J/ruAMsa53pVQWN7FLK88i5on7m.7Eg*cakseAvA4U3nvfCtBQb13*qx9LC8WOXpucZ7b.U7creo*ZoZKj1yJ7TGoLYFl96FILKAxDTeZvzIg!/b&bo=7wk*Bu8JPwYDByI!&rf=viewer_4");
                String tag = fragment.getSign();
                if("0".equals(tag)) {
                    pictureCropping2(imageUri);
                }else {
                    //图片剪裁
                    pictureCropping(imageUri);
                }
            }
        }else if(requestCode == PICTURE_CROPPING_CODE && resultCode == RESULT_OK) {
            //图片剪裁返回
            Bundle bundle = data.getExtras();
            Log.d("xxxxxx", "ooo");
            if (bundle != null) {
                Log.d("xxxxxx", "eee");
                //在这里获得了剪裁后的Bitmap对象，可以用于上传
                map = bundle.getParcelable("data");
                File file = saveImageToGallery(map);
                uploadFanganFile(file);
//                deleteSuccess(this, file.getName());

            }
        }else if(resultCode == RESULT_OK) {
            fragments.PbLoading_1();
            if (requestCode == 65636) {

                //打开相册返回
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                final Uri imageUri = Objects.requireNonNull(data).getData();
                Cursor cursor = getContentResolver().query(imageUri, filePathColumns, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
                //获取图片路径
                String imagePath = cursor.getString(columnIndex);
                cursor.close();
                //识别
                localImageDiscern(imagePath);
            }else {
                String imagePath = fragments.recognition();
                localImageDiscern(imagePath);
            }
        }
    }

    //保存图片
    public File saveImageToGallery(Bitmap bitmap) {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权的话调用ActivityCompat.requestPermissions（）方法向客户申请授权
            //第一个参数是Activity实例 第二个是String数组 将需要申诉的权限名放入即可 第三个是请求码 只要是唯一值即可
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "android");
        if (!appDir.exists()) {
            // 目录不存在 则创建
            appDir.mkdirs();
        }
        //下面的CompressFormat.PNG/CompressFormat.JPEG， 这里对应.png/.jpeg
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // 保存bitmap至本地
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.d("Personal_TAG", "保存图片异常" + e.toString());
            e.printStackTrace();
        } finally {
            if (bitmap!=null&&!bitmap.isRecycled()) {
                //当存储大图片时，为避免出现OOM ，及时回收Bitmap
                //bitmap.recycle();
                // 通知系统回收
                System.gc();
            }
            //返回保存的图片路径
            return file;
        }
    }


    //删除图片
    public static void deleteSuccess(Context context, String filePath) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + filePath + "'";
        //删除图片
        mContentResolver.delete(uri, where, null);
    }

    //上传服务器
    public void uploadFanganFile(File file) {
        String tag = fragment.getSign();
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body;
//        if("0".equals(tag)) {
//            body = MultipartBody.Part.createFormData("headSculpture", file.getName(), requestFile);
//        }else {
//            body = MultipartBody.Part.createFormData("background", file.getName(), requestFile);
//        }
//        Retrofit retrofit = new RetrofitBase().getRetrofit();
//        NameRequest nameRequest = retrofit.create(NameRequest.class);

//        Call<IClass3> call;
//        if("0".equals(tag)) {
//            call = nameRequest.upload2(RetrofitBase.mobileToken, RetrofitBase.uid, body);
//        }else {
//            call = nameRequest.upload1(RetrofitBase.mobileToken, RetrofitBase.uid, body);
//        }
//        call.enqueue(new Callback<IClass3>() {
//            @Override
//            public void onResponse(Call<IClass3> call, Response<IClass3> response) {
//                Message message = new Message();
//                message.obj = response.body().getCode();
//                if(message.obj.equals("200")) {
//                    String tag = fragment.getSign();
//                    if("0".equals(tag)) {
//                        Personal_Fragment.dataClass.setHead_sculpture(response.body().getData());
//                    }else {
//                        Personal_Fragment.dataClass.setBackground(response.body().getData());
//                    }
//                    Log.d("viper", response.body().getData());
//
//
//                }
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onFailure(Call<IClass3> call, Throwable t) {
//                Log.d("Personal_TAG", "请求失败" + t.toString());
//            }
//        });
        Message message = new Message();
        message.obj = "200";
        String tag1 = fragment.getSign();
        if("0".equals(tag1)) {
            Personal_Fragment.dataClass.setHead_sculpture("http://phototj.photo.store.qq.com/psc?/V506PzcE3M9HJv06WJzs3aG2Lm3l2E9J/ruAMsa53pVQWN7FLK88i5on7m.7Eg*cakseAvA4U3nvQe*4lzdavfmGOQ9*uGGXKBsrTDymCeeoRESaDTPO4asvzywXXtJXaOX4KhgzeeMs!/b&bo=YAlABmAJQAYDByI!&rf=viewer_4");
        }else {
            Personal_Fragment.dataClass.setBackground("http://m.qpic.cn/psc?/V10JG2ek3hqAnT/ruAMsa53pVQWN7FLK88i5r.79Xn0Bn3vTtBqRcD8czJMZMOV4K1Rajw*rnY420RWh7qDY94*doE97W2EuaZalXm4XPLg6izcaQKClg1UVzQ!/b&bo=OASgBTgEoAUBFzA!&rf=viewer_4");
        }
        handler.sendMessage(message);
    }

    //申请权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发给 EasyPermissions (添加的依赖库中的）
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


    //裁剪头像 圆形
    //裁剪头像
    private void pictureCropping(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1080);
        intent.putExtra("aspectY", 750);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 432);
        intent.putExtra("outputY", 300);

        // 返回裁剪后的数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICTURE_CROPPING_CODE);

    }

    //裁剪背景 长方形
    private void pictureCropping2(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 返回裁剪后的数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICTURE_CROPPING_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = ServiceGenerator.createService(ApiService.class);
//        requestApiGetToken();
        bottomSheetDialog = new BottomSheetDialog(this);

        bottomNavigationView = findViewById(R.id.main_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        //获取用户全部信息
        Requests.Request1(handler);
//        Message message = new Message();
//        message.obj = "200";
//        handler.sendMessage(message);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //点击替换碎片
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.home:
                //首页
                fragmentTransaction.replace(R.id.main_frame, new HomeFragment()).commit();
                break;
            case R.id.ball_game:
                //球局
                fragmentTransaction.replace(R.id.main_frame, new CourtFragment()).commit();
                break;
            case R.id.personal:
                //个人
                //替换碎片
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
     * 本地图片识别
     */
    private void localImageDiscern(String imagePath) {

        try {
            if (Photograph_Fragment.accessToken == null) {
                showMsg("获取AccessToken到null");
                return;
            }
            //通过图片路径显示图片
            fragments.ivPicture_1(imagePath);
            //按字节读取文件
            byte[] imgData = FileUtil.readFileByBytes(imagePath);
            //字节转Base64
            String imageBase64 = Base64Util.encode(imgData);
            //图像识别
            ImageDiscern(Photograph_Fragment.accessToken, imageBase64, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 图像识别请求
     *
     * @param token       token
     * @param imageBase64 图片Base64
     * @param imgUrl      网络图片Url
     */
    private void ImageDiscern(String token, String imageBase64, String imgUrl) {
        service.getDiscernResult1(token, imageBase64, imgUrl).enqueue(new Callback<GetDiscernResultResponse>() {
            @Override
            public void onResponse(Call<GetDiscernResultResponse> call, Response<GetDiscernResultResponse> response) {
                List<GetDiscernResultResponse.ResultBean> result = response.body() != null ? response.body().getResult() : null;
                if (result != null && result.size() > 0) {
                    //显示识别结果
                    fragments.showDiscernResult(result);
                } else {
                    fragments.PbLoading_2();
                    showMsg("未获得相应的识别结果");
                }
            }

            @Override
            public void onFailure(Call<GetDiscernResultResponse> call, Throwable t) {
                fragments.PbLoading_2();
                Log.e(TAG, "图像识别失败，失败原因：" + t);
            }

        });
    }

    /**
     * Toast提示
     * @param msg 内容
     */
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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