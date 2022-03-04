package com.example.personal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


import Adapter.Personal_Adapter;
import Fragments.Personal_Fragment;
import Tool.Tools;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@Route(path = "/personal/personal")
public class PersonalActivity extends AppCompatActivity {
    private static Bitmap bitmap;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;
    private List<View> head;
    private ImageView imageView;
    private CircleImageView circleImageView;
    private Personal_Fragment fragment;
    /**
     * 外部存储权限请求码
     */
    public static final int REQUEST_EXTERNAL_STORAGE_CODE = 9527;
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



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        fragment = new Personal_Fragment(this, this);
        replaceFragment(fragment);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.personal_frame, fragment);
        transaction.commit();
    }

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
                    Log.d("xxxxxx", "ooo");
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    //设置到ImageView上
                    String tag = fragment.getSign();
                    if("0".equals(tag)) {
                        head = fragment.getList1();
                        circleImageView = fragment.getCircleImageView();
                        for(View view : head) {
                            Log.d("xxxxxxxx", view.toString());
                            Glide.with(this).load(image).apply(requestOptions).into((CircleImageView) view);
                        }
                        Glide.with(this).load(image).apply(requestOptions).into(circleImageView);
                    }else {

                        imageView = fragment.getImageView();
                        //imageView.setImageBitmap(image);
                        Glide.with(this).load(image).into(imageView);
                    }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发给 EasyPermissions (添加的依赖库中的）
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_EXTERNAL_STORAGE_CODE)
    //注解的意思是权限通过后在调用一次该方法 根据内部参数权限码
    private void requestPermission() {
        //一个权限组只要有一个权限通过则代表整组权限通过
        String[] param = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this, param)) {
            showMsg("已获得权限");
        }else {
            //无权限 则进行权限请求
            EasyPermissions.requestPermissions(this,"请求权限",REQUEST_EXTERNAL_STORAGE_CODE,param);
        }
    }

    private void showMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

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

}