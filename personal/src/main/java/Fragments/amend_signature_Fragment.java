package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.personal.R;

import Tool.ReplaceFragment;

public class amend_signature_Fragment extends Fragment implements View.OnClickListener{
    Button back;
    TextView over;
    private Context context;
    private FragmentTransaction fragmentTransaction;
    private EditText signature_signature;

    public amend_signature_Fragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.amend_signature, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        signature_signature = view.findViewById(R.id.signature_signature);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        back = view.findViewById(R.id.signature_back);
        back.setOnClickListener(this);
        over = view.findViewById(R.id.signature_over);
        over.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.signature_over) {
            Personal_Fragment.dataClass.setSignature(signature_signature.getText().toString());
            Personal_Fragment.signature.setText(signature_signature.getText().toString());

            getActivity().onBackPressed();
            //ReplaceFragment.showFragment(fragmentTransaction, this, new Personal_Fragment(context, getActivity()));
        } else if (id == R.id.signature_back) {
            getActivity().onBackPressed();
            //ReplaceFragment.showFragment(fragmentTransaction, this, new Personal_Fragment(context, getActivity()));
        }
    }
}
