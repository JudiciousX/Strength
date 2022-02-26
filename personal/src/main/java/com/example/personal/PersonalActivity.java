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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.io.FileNotFoundException;
import java.io.IOException;

import Adapter.Personal_Adapter;
import Fragments.Personal_Fragment;
import Tool.Tools;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/personal/personal")
public class PersonalActivity extends AppCompatActivity {
    private static Bitmap bitmap;
    private int SELECT_PICTURE = 0x00;
    private int SELECT_CAMER = 0x01;

    private ImageView imageView;
    private CircleImageView circleImageView;
    private Personal_Fragment fragment;


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
        if(resultCode != RESULT_OK) {
            return;
        }
        if(requestCode == SELECT_PICTURE) {
            String tag = fragment.getSign();
            bitmap = new Tools().handle(resultCode, data, bitmap, this);
            if("0".equals(tag)) {
                circleImageView = fragment.getCircleImageView();
//                try {
//                    new Tools().setPicToView(data, circleImageView, tag, this, this);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                circleImageView.setImageBitmap(bitmap);
            }else {
                imageView = fragment.getImageView();
//                try {
//                    new Tools().setPicToView(data, imageView, tag,  this, this);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                imageView.setImageBitmap(bitmap);
            }
        }else if(requestCode == SELECT_CAMER) {
            Log.i("TAG", "相机");
            if(data.getData() == null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                String tag = fragment.getSign();
                if("0".equals(tag)) {
                    circleImageView = fragment.getCircleImageView();
                    circleImageView.setImageBitmap(bitmap);
                }else {
                    imageView = fragment.getImageView();
                    imageView.setImageBitmap(bitmap);
                }
            }else try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                String tag = fragment.getSign();
                if("0".equals(tag)) {
                    circleImageView = fragment.getCircleImageView();
                    circleImageView.setImageBitmap(bitmap);
                }else {
                    imageView = fragment.getImageView();
                    imageView.setImageBitmap(bitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }

    }
}