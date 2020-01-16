package rest;

import java.util.List;

import model.Author;
import model.Book;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("/books")
    Call<List<Book>>getAllBooks();

    @GET("/Authors")
    Call<List<Author>>getAllAuthors();

}
