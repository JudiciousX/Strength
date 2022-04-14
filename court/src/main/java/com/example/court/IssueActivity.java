package com.example.court;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.commlib.RetrofitBase;
import com.google.gson.Gson;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.alibaba.android.arouter.compiler.utils.Consts.TAG;

public class IssueActivity extends AppCompatActivity {

    public static Data data = new Data();
    private EditText address,content;
    private TextView date;
    private String s;
    private StringBuilder up = new StringBuilder();
    private Button commit;
    private Calendar calendar,minCalendar,maxCalendar;
    int mHour, mMinute,year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        ActionBar actionBar = getSupportActionBar();//隐藏标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        date = findViewById(R.id.court_time_text);
        address = findViewById(R.id.court_local_issue);
        content = findViewById(R.id.court_context);
        commit = findViewById(R.id.issue_publish);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                minCalendar = Calendar.getInstance();

                minCalendar.set(Calendar.DAY_OF_MONTH, minCalendar.get(Calendar.DAY_OF_MONTH));

                maxCalendar = Calendar.getInstance();
                maxCalendar.add(Calendar.MONTH, 1);//设置年的范围（今年是2022，第二个参数是2则，datepicker范围为2016-2018）

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog.THEME_HOLO_LIGHT
                DatePickerDialog dialog=new DatePickerDialog(IssueActivity.this,R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                s=Integer.toString(monthOfYear+1)+"月"+Integer.toString(dayOfMonth)+"日";

                                    up.append(year+"-");
                                    up.append((monthOfYear+1)< 10 ? "0" + (monthOfYear+1) : (monthOfYear+1));
                                    up.append("-");
                                    up.append( dayOfMonth < 10 ? "0" + dayOfMonth:dayOfMonth+" ");
                                Log.d("dddddd",up.toString());
                                date.setText(up.toString());
                                setDate(v);
                            }
                        },calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //设置起始日期和结束日期
                DatePicker datePicker = dialog.getDatePicker();
                datePicker.setMinDate(minCalendar.getTimeInMillis());
                datePicker.setMaxDate(maxCalendar.getTimeInMillis());
                dialog.show();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setTime(date.getText().toString());
                data.setAddress(address.getText().toString());
                data.setContent(content.getText().toString());
                Request_Content();
                finish();
            }
        });
    }

    public void setDate(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IssueActivity.this,R.style.DatePickerTheme);
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.time_dialog, null);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        //初始化时间
        if (calendar != null) {
            calendar.setTimeInMillis(System.currentTimeMillis());

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);
        }
        //设置time布局
        builder.setView(view);
        builder.setTitle("设置时间信息");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHour = timePicker.getCurrentHour();
                mMinute = timePicker.getCurrentMinute();
                //时间小于10的数字 前面补0 如01:12:00
                date.setText(up.toString()+new StringBuilder().append(mHour < 10 ? "0" + mHour : mHour).append(":")
                        .append(mMinute < 10 ? "0" + mMinute : mMinute));
                dialog.cancel();
            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public void Request_Content(){
        Retrofit retrofit = new RetrofitBase().getRetrofit();
        Api api = retrofit.create(Api.class);
        Gson gson = new Gson();
        String contentBean = gson.toJson(IssueActivity.data,Data.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),contentBean);
        Call<Article> jsonPost = api.postDataCall(RetrofitBase.mobileToken,RetrofitBase.uid,body);
        jsonPost.enqueue(new Callback<Article>(){
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Toast.makeText(IssueActivity.this, "get回调成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e(TAG, "get回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(IssueActivity.this, "get回调失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
