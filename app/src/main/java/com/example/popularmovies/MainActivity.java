package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.popularmovies.Movies.Movies;
import com.example.popularmovies.Movies.MoviesRespons;
import com.example.popularmovies.Movies.MoviesServesis;
import com.example.popularmovies.databaseRoom.CrateResponeDataBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {



    private static final String MyKey = "Your's Key!!";
    private  static final String FOR_FLAG="STATE_INSTANT";
    private static final String REC_VIEW_LAYOUT="REC";



    RecyclerView recyclerView;
    MyAdapter adapter;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    List<Movies> movies;
    public Call <MoviesRespons>call;
    public  int popular ;
    Parcelable parcelable;
    int numofcoulm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.indeterminateBar);
        numofcoulm=calculateNoOfColumns(getApplicationContext());



        movies = new ArrayList<>();

        if(savedInstanceState != null ){
            parcelable=savedInstanceState.getParcelable(REC_VIEW_LAYOUT);
        }
        if (savedInstanceState != null) {
            popular = savedInstanceState.getInt(FOR_FLAG);
        } else {
            popular = 1;
        }
        if (popular == 2)
            setFavoriteFromDataBase();
        else
        fetchData(popular);


        inilization(movies,parcelable,numofcoulm);
        ForShowProgress();
        Title(popular);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FOR_FLAG, popular);
        if (popular == 1) {
            outState.putParcelable(REC_VIEW_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        } else if (popular == 0) {
            outState.putParcelable(REC_VIEW_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.popular_movie_menu) {
            popular = 1;
           ForShowProgress();
            fetchData(popular);

        }  if (id == R.id.toprate_movies_menu) {
            popular = 0;
            ForShowProgress();
            fetchData(popular);

        }else if (id == R.id.favorite){
            popular=2;
            ForShowProgress();
            setFavoriteFromDataBase();
        }
        Title(popular);


        return super.onOptionsItemSelected(item);
    }

    private void Title(int popular) {
        if(popular == 1) {
            setTitle("Popular Movies");
        }else if(popular == 0) {
            setTitle("Top Movies");
        } else if(popular == 2) {
            setTitle("Favorite");
        }
    }

    public  void ForShowProgress(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void setFavoriteFromDataBase (){
        CrateResponeDataBase.getInstance(MainActivity.this)
                .getData()
                .getAllMovies()
                .observe(this, new Observer<List<Movies>>() {
                    @Override
                    public void onChanged(List<Movies> movies) {
                        SetInRecView(movies,parcelable);

                    }
                });
    }



    private void fetchData(int DatatopOrpopular) {
        //Genrate the services

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        MoviesServesis service = retrofit.create(MoviesServesis.class);
        switch (DatatopOrpopular) {
            case 0:
                //Run the requst
                call = service.getTop(MyKey);
                break;

            case 1:
                call = service.getpopular(MyKey);
                    break;
        }

        // Do something after 5s = 5000ms
      call.enqueue(new Callback<MoviesRespons>() {
            @Override
            public void onResponse(Call<MoviesRespons> call, Response<MoviesRespons> response) {
                //Upadte Ui
                //Respons Movies
                if (response.isSuccessful()) {

                    MoviesRespons moviesRespons = response.body();
                    movies = moviesRespons.getmResults();

                    SetInRecView(movies,parcelable);
                }


                return;
            }

            @Override
            public void onFailure(Call<MoviesRespons> call, Throwable t) {
                //display err !! ok
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT);

            }
        });

    }

    private void SetInRecView(List<Movies> movies,Parcelable parcelable) {
        adapter = new MyAdapter(MainActivity.this, movies);
        recyclerView.setAdapter(adapter);
        if(parcelable !=null){
            inilization(movies,parcelable,numofcoulm);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void inilization(List <Movies> movies , Parcelable parcelable,int numofcoulm) {


        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,numofcoulm);

        if(parcelable != null){
            layoutManager.onRestoreInstanceState(parcelable);
        }
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(MainActivity.this, movies);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int nUMOfColumns = (int) (dpWidth / 180);
        return nUMOfColumns;
    }


}

















