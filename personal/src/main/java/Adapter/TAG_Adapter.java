package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

import Fragments.Personal_Fragment;

public class TAG_Adapter extends RecyclerView.Adapter<TAG_Adapter.ViewHolder> {
    private TextView personal_tag1;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public TAG_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tags, parent, false);
        personal_tag1 = view.findViewById(R.id.personal_tag1);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TAG_Adapter.ViewHolder holder, int position) {
        personal_tag1.setText(Personal_Fragment.tags.get(position));
    }

    @Override
    public int getItemCount() {
        return Personal_Fragment.tags.size();
    }
}
