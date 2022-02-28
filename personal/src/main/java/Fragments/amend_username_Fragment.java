package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.personal.R;

public class amend_username_Fragment extends Fragment implements View.OnClickListener {
    Button back;
    TextView over;
    private FragmentTransaction fragmentTransaction;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.amend_username, container, false);
        init(view);
        return view;
    }

    public amend_username_Fragment( Context context) {
        this.context = context;
    }

    private void init(View view) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        back = view.findViewById(R.id.username_back);
        back.setOnClickListener(this);
        over = view.findViewById(R.id.username_over);
        over.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username_over:
            case R.id.username_back:
                fragmentTransaction.replace(R.id.personal_frame, new Personal_Fragment(context, getActivity())).commit();
                break;
        }
    }
}
