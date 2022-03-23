package com.example.court;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class IssueActivity extends AppCompatActivity {

    TextView date;
    private String s;
    Calendar calendar,minCalendar,maxCalendar;
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
                                date.setText(s);
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
                date.setText(s+new StringBuilder().append(mHour < 10 ? "0" + mHour : mHour).append(":")
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
}
