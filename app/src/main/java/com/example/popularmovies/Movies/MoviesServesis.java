package com.example.popularmovies.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
@SuppressWarnings("ALL")
public interface MoviesServesis {

    //https://api.themoviedb.org/3/movie/550?api_key=

    @GET("movie/popular")
    Call<MoviesRespons> getpopular(@Query("api_key") String token);

    @GET("movie/top_rated")
    Call<MoviesRespons> getTop(@Query("api_key")String token);

}
