package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Blogs_Adapter extends RecyclerView.Adapter<Blogs_Adapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView head;
        private TextView user;
        private TextView time;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.personal_head);
            user = itemView.findViewById(R.id.personal_user);
            time = itemView.findViewById(R.id.personal_time);
            description = itemView.findViewById(R.id.personal_description);
        }
    }

    @Override
    public Blogs_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_blogs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Blogs_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
