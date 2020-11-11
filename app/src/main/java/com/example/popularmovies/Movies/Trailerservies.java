package com.example.popularmovies.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
@SuppressWarnings("ALL")

public interface Trailerservies {

    @GET("movie/{id}/reviews")
    Call<ReviewRespons> getReviews(@Path("id") int movieId, @Query("api_key") String token);
    @GET("movie/{id}/videos")
    Call<TrailaerRespones> getTrailers(@Path("id") int movieId, @Query("api_key") String token);
}
