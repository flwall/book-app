package activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.Objects;

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
        String bookjson = (String) Objects.requireNonNull(getIntent().getExtras()).get("book");
        book = new Gson().fromJson(bookjson, Book.class);

        title = findViewById(R.id.title_textview);
        title.setText(book.getTitle());
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(book.getRating());
        ratingBar.setEnabled(false);


        EditText desc = findViewById(R.id.descriptionField);
        desc.setText(book.getDescription());
        desc.setKeyListener(null);

    }


}
