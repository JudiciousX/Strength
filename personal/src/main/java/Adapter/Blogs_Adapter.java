package Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.personal.R;

import java.util.ArrayList;
import java.util.List;

import Fragments.Personal_Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Blogs_Adapter extends RecyclerView.Adapter<Blogs_Adapter.ViewHolder> {
    private CircleImageView head;
    private TextView user;
    private TextView time;
    private TextView description;
    private List<View> head1 = new ArrayList<>();
    private List<View> user1 = new ArrayList<>();
    private List<View> time1 = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public Blogs_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_blogs, parent, false);
        head = view.findViewById(R.id.personal_head);
        user = view.findViewById(R.id.personal_user);
        time = view.findViewById(R.id.personal_time);
        description = view.findViewById(R.id.personal_description);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Blogs_Adapter.ViewHolder holder, int position) {
        user.setText(Personal_Fragment.dataClass.getUsername());
        head1.add(head);
        user1.add(user);
        time1.add(time);
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public List<View> getHead1() {
        return head1;
    }

    public List<View> getTime1() {
        return time1;
    }

    public List<View> getUser1() {
        return user1;
    }
}
