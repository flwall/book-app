package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import adapter.BookAdapter;
import at.ac.htlperg.bookmanagement.R;
import model.Book;
import rest.GetDataService;
import rest.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment {
    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Book>> call = service.getAllBooks();

        call.enqueue(new Callback<List<Book>>() {
            private String TAG = getClass().getSimpleName();
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                Toast.makeText(getContext(), "Loading Books..", Toast.LENGTH_SHORT).show();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(final List<Book> bookList) {
        recyclerView = getView().findViewById(R.id.customRecyclerView);
        bookAdapter = new BookAdapter(getContext(), bookList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                openDetailActivity(bookList.get(position));
            }

        }));
    }

    private void openDetailActivity(Book book){
        Intent myIntent = new Intent(getContext(), DetailBookActivity.class);
        myIntent.putExtra("book", new Gson().toJson(book, Book.class));
        startActivity(myIntent);
    }

}
