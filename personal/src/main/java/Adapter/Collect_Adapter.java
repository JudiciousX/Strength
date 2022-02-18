package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

public class Collect_Adapter extends RecyclerView.Adapter<Collect_Adapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView arena;
        private TextView place;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            arena = itemView.findViewById(R.id.personal_arena);
            place = itemView.findViewById(R.id.personal_place);
        }
    }

    @NonNull
    @Override
    public Collect_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_collect, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Collect_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
