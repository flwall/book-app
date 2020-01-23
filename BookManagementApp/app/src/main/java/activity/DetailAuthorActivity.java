package activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import at.ac.htlperg.bookmanagement.R;
import model.Author;

public class DetailAuthorActivity extends AppCompatActivity {
    private Author author;
    TextView authorname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_author_activity);
        String authorjson = (String)getIntent().getExtras().get("author");
        author = new Gson().fromJson(authorjson, Author.class);

        authorname = findViewById(R.id.authorname);
        authorname.setText(author.getName());
    }
}
