package com.example.android.popularmovieactivity;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MoviesAPI {

    @GET("/3/movie/{category}")

    Call<PopularMovie.MovieResult> getMovies(
            @Path("category") String category,
            @Query("api_key") String api_key
    );

}
