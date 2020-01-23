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

import adapter.AuthorAdapter;
import at.ac.htlperg.bookmanagement.R;
import model.Author;
import model.Book;
import rest.GetDataService;
import rest.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorFragment extends Fragment {

    private AuthorAdapter authorAdapter;
    private RecyclerView recyclerView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_book, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Author>> call = service.getAllAuthors();

        call.enqueue(new Callback<List<Author>>() {
            private String TAG = getClass().getSimpleName();
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                Toast.makeText(getContext(), "Loading Authors..", Toast.LENGTH_SHORT).show();
                //Log.e(TAG, response.body().toString());
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(final List<Author> authorList) {
        recyclerView = getView().findViewById(R.id.customRecyclerView);
        authorAdapter = new AuthorAdapter(getContext(), authorList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(authorAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openDetailActivity(authorList.get(position));
            }
        }));
    }

    private void openDetailActivity(Author author){
        Intent myIntent = new Intent(getContext(), DetailAuthorActivity.class);
        myIntent.putExtra("author", new Gson().toJson(author, Book.class));
        startActivity(myIntent);
    }

}
