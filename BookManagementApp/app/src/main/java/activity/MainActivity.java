package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adapter.BookAdapter;
import at.ac.htlperg.bookmanagement.R;
import model.Book;
import rest.GetDataService;
import rest.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Book>> call = service.getAllBooks();
        call.enqueue(new Callback<List<Book>>() {
            private String TAG = getClass().getSimpleName();


            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Book> bookList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        bookAdapter = new BookAdapter(this, bookList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);
    }
}
