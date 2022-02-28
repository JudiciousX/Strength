package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.personal.R;

import java.util.List;

import Tool.DatePickerDialog;
import Tool.DateUtil;

public class amend_data_Fragment extends Fragment implements View.OnClickListener {
    Context context;
    Button back;
    TextView over;
    TextView birthday;
    private Dialog dateDialog;
    private FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.amend_data, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        back = view.findViewById(R.id.data_back);
        back.setOnClickListener(this);
        over = view.findViewById(R.id.data_over);
        over.setOnClickListener(this);
        birthday = view.findViewById(R.id.data_birthday);
        birthday.setOnClickListener(this);
    }

    public amend_data_Fragment(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Birthday() {
        showDateDialog(DateUtil.getDateForString("1990-01-01"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(context);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {

                birthday.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));

            }

            @Override
            public void onCancel() {

            }
        })

                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.data_over:
            case R.id.data_back:
                fragmentTransaction.replace(R.id.personal_frame, new Personal_Fragment(context, getActivity())).commit();
                break;
            case R.id.data_birthday:
                Birthday();
                break;

        }
    }
}
