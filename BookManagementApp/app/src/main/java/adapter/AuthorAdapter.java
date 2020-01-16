package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.ac.htlperg.bookmanagement.R;
import model.Author;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.CustomViewHolder>{

    private List<Author> authorList;
    private Context context;

    public AuthorAdapter(Context context, List<Author> authorList){
        this.context = context;
        this.authorList = authorList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);

        }
    }

    @Override
    public AuthorAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new AuthorAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuthorAdapter.CustomViewHolder holder, int position) {
        holder.txtTitle.setText(authorList.get(position).getName());

        /*Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(bookList.get(position).getCover())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);*/
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }
}
