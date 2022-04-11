package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Attention_Adapter extends RecyclerView.Adapter<Attention_Adapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        private ViewStub viewStub;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.attention_head);
            viewStub = itemView.findViewById(R.id.emptyView);
            textView = itemView.findViewById(R.id.attention_username);
        }
    }


    @NonNull
    @Override
    public Attention_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_attention, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Attention_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
