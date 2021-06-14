package Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import Model.Blog;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private Context context;
    private final List<Blog> blogList;

    public RecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        String imageUrl = null;
        holder.Title.setText(blog.getTitle());
        holder.Description.setText(blog.getDescription());
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new  Date(Long.valueOf(blog.getTimeStamp())).getTime());
        holder.timeStamp.setText(formattedDate);
        imageUrl = blog.getImage();
        //TODO: Use Picasso library to load image
        Picasso.get().load(imageUrl).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView Description;
        public TextView timeStamp;
        public ImageView image;
        String userId;
        public ViewHolder(View view, Context ctx) {
            super(view);
            context = ctx;
            Title = (TextView) view.findViewById(R.id.postTitleList);
            Description = (TextView) view.findViewById(R.id.postTextList);
            image = (ImageView) view.findViewById(R.id.postImageList);
            timeStamp = (TextView) view.findViewById(R.id.timestampList);
            userId = null;
        }
    }
}
