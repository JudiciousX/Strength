package com.example.personal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.commlib.RetrofitBase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;


import Fragments.Personal_Fragment;
import IClass.IClass;
import IRequest.NameRequest;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Route(path = "/personal/personal")
public class PersonalActivity extends AppCompatActivity {
    private static Bitmap bitmap;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;
    private List<View> head;
    private ImageView imageView;
    private CircleImageView circleImageView;
    private Personal_Fragment fragment;
    private Context context = this;
    private Bitmap map;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case "200":
                    //设置到ImageView上
                    String tag = fragment.getSign();
                    if("0".equals(tag)) {
                        head = fragment.getList1();
                        circleImageView = fragment.getCircleImageView();
                        for(View view : head) {
                            Log.d("xxxxxxxx", view.toString());
                            Glide.with(context).load(map).apply(requestOptions).into((CircleImageView) view);
                        }
                        Glide.with(context).load(map).apply(requestOptions).into(circleImageView);
                    }else {
                        imageView = fragment.getImageView();
                        //imageView.setImageBitmap(image);
                        Glide.with(context).load(map).into(imageView);
                    }
                    break;
                case "500":
                    Toast.makeText(context, "服务器异常，上传失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
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
        fragment = new Personal_Fragment(this, this, 2131230948);
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
                    Log.d("xxxxxx", "eee");
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    map = bundle.getParcelable("data");
//                    Retrofit retrofit = new IRetrofit().getRetrofit();
//
//                    NameRequest nameRequest = retrofit.create(NameRequest.class);
//                    //File file = new File(Environment.getExternalStorageDirectory(), Const.licensepicture);
//                    File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
//                    RequestBody requestFile =
//                            RequestBody.create(MediaType.parse("image/jpg"), file);
//                    Log.d("xxxxxx", file.toString());
//                    Call<IClass> uploadPicture = nameRequest.upload1(requestFile);
//                    uploadPicture.enqueue(new Callback<IClass>() {
//                        @Override
//                        public void onResponse(Call<IClass> call, Response<IClass> response) {
//                            Log.e("xxxxxx", "onResponseonResponseonResponse: "+response.body().toString() );
//                            //Toast.makeText(uploadLicensePicture.this, (CharSequence) response.body(), Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<IClass> call, Throwable t) {
//                            Log.e("xxxxx", "onFailureonFailureonFailureonFailureonFailureonFailure: " );
//                        }
//                    });

//                    File photoFileDir = new File(Environment.getExternalStorageDirectory() + "/ClipHeadPhoto/cache/");
//                    if(!photoFileDir.exists()){    // 如果路径不存在，就创建路径
//                        photoFileDir.mkdirs();
//                    }
//                    File photoFile = new File(photoFileDir, "photo");

//                    File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
//                    if (!file.exists()) {
//                        file.mkdirs();
//                    }
                    //File file = new File(Environment.getExternalStorageDirectory(), Const.licensepicture);
//                    FileOutputStream fileOutStream = null;
//                    try {
//                        fileOutStream = new FileOutputStream(file);
//                    } catch (FileNotFoundException e) {
//                        file.delete();
//                        Log.d("xxxxxx", "xxx");
//                        e.printStackTrace();
//
//                    }
//                    try {
//
//                        image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream);
//                        fileOutStream.flush();
//                        fileOutStream.close();
//                        uploadImage(file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    File file = saveImageToGallery(map);
                    Log.d("xxxxx", file.toString());
                    uploadFanganFile(file);
                    deleteSuccess(this, file.getName());

            }
        }
    }

    //保存图片
    public File saveImageToGallery(Bitmap bitmap) {
        if(ContextCompat.checkSelfPermission(PersonalActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权的话调用ActivityCompat.requestPermissions（）方法向客户申请授权
            //第一个参数是Activity实例 第二个是String数组 将需要申诉的权限名放入即可 第三个是请求码 只要是唯一值即可
            ActivityCompat.requestPermissions(PersonalActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
            Log.d("xxxxx", e.toString());
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

    public void uploadFanganFile(File file) {
        //File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("background", file.getName(), requestFile);
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        NameRequest nameRequest = retrofit.create(NameRequest.class);
        Log.d("xxxxxx", body.toString());
        nameRequest.upload1("944348013390725120",body).enqueue(new Callback<IClass>() {
            @Override
            public void onResponse(Call<IClass> call, Response<IClass> response) {
                Message message = new Message();
                message.obj = response.body().getCode();
                Log.d("xxxxxx", response.body().getCode());
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<IClass> call, Throwable t) {
                Log.d("xxxxxx", t.toString());
            }
        });
    }


    //上传图片至服务器
//    private void uploadImage(File file) {
//        Retrofit retrofit = new IRetrofit().getRetrofit();
//        NameRequest nameRequest = retrofit.create(NameRequest.class);
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//        Log.d("xxxxx", requestBody.toString());
//        Log.d("xxxxx", file.toString());
//        nameRequest.upload1(requestBody).enqueue(new Callback<IClass>() {
//            @Override
//            public void onResponse(Call<IClass> call, Response<IClass> response) {
//                Message message = new Message();
//                message.obj = response.body().getCode();
//                Log.d("xxxxxx", response.body().getCode());
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onFailure(Call<IClass> call, Throwable t) {
//                Log.d("TAG", "请求失败");
//            }
//        });
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发给 EasyPermissions (添加的依赖库中的）
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You agree the permission", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
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