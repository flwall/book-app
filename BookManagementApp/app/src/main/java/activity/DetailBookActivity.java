package activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import at.ac.htlperg.bookmanagement.R;
import model.Book;

public class DetailBookActivity extends AppCompatActivity {

    private Book book;
    TextView title;
    RatingBar ratingBar;
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_book_activity);
        String bookjson = (String)getIntent().getExtras().get("book");
        book = new Gson().fromJson(bookjson, Book.class);

        title = findViewById(R.id.title_textview);
        title.setText(book.getTitle());
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(book.getRating());
        ratingBar.setEnabled(false);

        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        layerDrawable.getDrawable(2).setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        layerDrawable.getDrawable(1).setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        layerDrawable.getDrawable(0).setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);//when not selected
    }


}
