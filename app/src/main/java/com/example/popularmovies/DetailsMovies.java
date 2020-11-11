package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.Movies.Movies;
import com.example.popularmovies.Movies.Review;
import com.example.popularmovies.Movies.ReviewRespons;
import com.example.popularmovies.Movies.TrailaerRespones;
import com.example.popularmovies.Movies.Trailerservies;
import com.example.popularmovies.Movies.Trialaer;
import com.example.popularmovies.databaseRoom.CrateResponeDataBase;
import com.example.popularmovies.databaseRoom.Executors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsMovies extends AppCompatActivity
{
    TextView relase_date;
    TextView rate_avg;
    ImageView image_movie;
    TextView name_movie;
    TextView plotSynopsis;
    RatingBar ratingBar;
    RecyclerView recyclerViewTrialaer;
    TrialaerAdapter trialaerAdapter;
    private static final String MyKey = "Your Keys!!";
    ProgressBar progressBar ;
    List<Trialaer> trialaerList;
    RecyclerView  recyclerViewReview ;
    ReviewAdapter  reviewAdapter;
    List <Review> reviewList ;
    FloatingActionButton floatingActionButton;
   public boolean isInNotFavorite= false;
    Movies movies;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movies);
            //Define
        relase_date=findViewById(R.id.relase_date);
        rate_avg=findViewById(R.id.rateing);
        image_movie=findViewById(R.id.image_movie);
        name_movie=findViewById(R.id.name_movie);
        plotSynopsis=findViewById(R.id.poltsyniosis);
        ratingBar = findViewById(R.id.rating);
        progressBar=findViewById(R.id.indeterminateBar);
        recyclerViewTrialaer=findViewById(R.id.rec_view_trialaer);
        trialaerList=new ArrayList<>();
        progressBar=findViewById(R.id.indeterminateBar);
        recyclerViewReview=findViewById(R.id.rec_view_review);
        reviewList=new ArrayList<>();
        floatingActionButton=findViewById(R.id.favorite);


        GetDataFromMyAdapter();
        initlizationFavorite();
        showVisibleProgressBar();
        // SET RECYCLE TRIALAER
        fetchDataTrialaer();
        recyclerViewTrialaer.setHasFixedSize(true);
        recyclerViewTrialaer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        trialaerAdapter=new TrialaerAdapter(trialaerList,DetailsMovies.this);
        recyclerViewTrialaer.setAdapter(trialaerAdapter);


        //SET RECYCLE REVIEW
        fetchDataReview();
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewReview.setHasFixedSize(true);
        reviewAdapter=new ReviewAdapter(reviewList, DetailsMovies.this);
        recyclerViewReview.setAdapter(reviewAdapter);

        //Check the List Movies
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                isInNotFavorite = CrateResponeDataBase.getInstance(DetailsMovies.this)
                        .getData()
                        .CheckMovies(movies.getmId()).size() > 0;
            }
        });





    }

    private void initlizationFavorite() {
            // Add to Favorite or Delete it .
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInNotFavorite) {
                    Executors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            CrateResponeDataBase.getInstance(DetailsMovies.this)
                                    .getData().delete(movies);

                                    isInNotFavorite=false;
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Toast.makeText(DetailsMovies.this,movies.getmTitel()+"Delete From Favorite",
                                             Toast.LENGTH_SHORT).show();
                                 }
                             });
                        }

                    });
                }else{
                    Executors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            CrateResponeDataBase.getInstance(DetailsMovies.this)
                                    .getData().addMovie(movies);

                            isInNotFavorite = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailsMovies.this,movies.getmTitel()+"ADD to Favorite",
                                            Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    });
                }


            }

        });
    }


    private void GetDataFromMyAdapter() {
        Intent intent  =getIntent();
        if (intent.hasExtra("original_title")) {

            String title= intent.getExtras().getString("original_title");
            String image = getIntent().getExtras().getString("poster_path");
            String descrpition = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String date_relase = getIntent().getExtras().getString("release_date");
            movies = (Movies)intent.getSerializableExtra("PositionMovie");
            Picasso.with(this).load(image)
                    .placeholder(R.drawable.load)
                    .into(image_movie);

            rate_avg.setText(rating);
            plotSynopsis.setText(descrpition);
            name_movie.setText(title);
            relase_date.setText(date_relase);
            float num_avg= Float.parseFloat(rating);
            float num = num_avg / 2 ;
            if(num < 5) {
                ratingBar.setRating(num);

            }




        }
        else{
            Toast.makeText(DetailsMovies.this,"There No Deatlis",Toast.LENGTH_LONG).show();
        }
    }
    private void showVisibleProgressBar() {
        recyclerViewTrialaer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void fetchDataTrialaer() {
        //Genrate the services

        Trailerservies service = getTrailerservies();
        int idTrialaer = getIntent().getExtras().getInt("idMovies");

        service.getTrailers(idTrialaer,MyKey).enqueue(new Callback<TrailaerRespones>() {
            @Override
            public void onResponse(Call<TrailaerRespones> call, Response<TrailaerRespones> response) {
                //Upadte Ui
                //Respons Movies
                if (response.isSuccessful()) {

                    TrailaerRespones trailaerRespones= response.body();
                    trialaerList = trailaerRespones.getuResult();
                    TrialaerAdapter trialaerAdapter = new TrialaerAdapter(trialaerList, DetailsMovies.this);
                    recyclerViewTrialaer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerViewTrialaer.setAdapter(trialaerAdapter);
                    recyclerViewTrialaer.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }


                return;

            }

            @Override
            public void onFailure(Call<TrailaerRespones> call, Throwable t) {
                //display err !! ok
                Toast.makeText(DetailsMovies.this, t.getMessage(), Toast.LENGTH_SHORT);

            }
        });

    }

    private Trailerservies getTrailerservies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(Trailerservies.class);
    }
    private void fetchDataReview() {
        //Genrate the services


        Trailerservies service = getTrailerservies();
        int idReview = getIntent().getExtras().getInt("idMovies");

        service.getReviews(idReview,MyKey).enqueue(new Callback<ReviewRespons>() {
            @Override
            public void onResponse(Call<ReviewRespons> call, Response<ReviewRespons> response) {
                //Upadte Ui
                //Respons Movies
                if (response.isSuccessful()) {

                    ReviewRespons reviewRespons= response.body();
                    reviewList = reviewRespons.getrResult();
                    ReviewAdapter reviewAdapter = new ReviewAdapter(reviewList, DetailsMovies.this);
                    recyclerViewReview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerViewReview.setAdapter(reviewAdapter);
                    recyclerViewReview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }


                return;

            }

            @Override
            public void onFailure(Call<ReviewRespons> call, Throwable t) {
                //display err !! ok
                Toast.makeText(DetailsMovies.this, t.getMessage(), Toast.LENGTH_SHORT);

            }
        });

    }


}
