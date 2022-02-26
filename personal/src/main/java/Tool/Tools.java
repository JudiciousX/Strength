package Tool;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tools {
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";
    public static final int RESULT_OK = -1;


    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
    public void startSmallPhotoZoom(Uri uri, Activity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

    public Bitmap handle(int resultCode, Intent data, Bitmap bitmap, Context context) {
        if(resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if(uri != null && data.getData() != null) {
                Log.i("TAG", "uri 和 data.getData()不为空");
                ContentResolver resolver = context.getContentResolver();
                if(bitmap != null) {
                    bitmap.recycle();
                }try{
                    bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                Log.i("TAG", "uri为空或者data为空   " + "数据：" + data.getData() + "  uri: " + uri);
            }
        }
        return bitmap;
    }

    /**
     * 小图模式中，保存图片后，设置到视图中
     */
    public void setPicToView(Intent data, View view, String tag, Context context, Activity activity) throws FileNotFoundException {
        Uri uri = data.getData();

        if (uri != null) {
            ContentResolver resolver = context.getContentResolver();
            Bitmap photo = BitmapFactory.decodeStream(resolver.openInputStream(uri)); // 直接获得内存中保存的 bitmap
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //没有授权的话调用ActivityCompat.requestPermissions（）方法向客户申请授权
                    //第一个参数是Activity实例 第二个是String数组 将需要申诉的权限名放入即可 第三个是请求码 只要是唯一值即可
                    ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CALL_PHONE}, 1);
                }else {

                }


            }
            // 在视图中显示图片
            if("0".equals(tag)) {
                CircleImageView circleImageView = (CircleImageView) view;
                circleImageView.setImageBitmap(photo);
            }else {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(photo);
            }
            //view.setImageBitmap(photo);
        }


    }



}
