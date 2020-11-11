package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.Movies.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Movies> movies;
    Context context ;


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardviewmain,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        holder.title.setText(movies.get(holder.getAdapterPosition()).getmTitel());
        Picasso.with(context).load(movies.get(holder.getAdapterPosition())
                .getmPoster_path()) .placeholder(R.drawable.load)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
       return (movies !=null) ? movies.size(): 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView image;
        int position;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_card);
            image=itemView.findViewById(R.id.image_card);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position=getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailsMovies.class);
                        intent.putExtra("idMovies",movies.get(position).getmId());
                        intent.putExtra("original_title", movies.get(position).getmOriginal());
                        intent.putExtra("poster_path", movies.get(position).getmPoster_path());
                        intent.putExtra("overview", movies.get(position).getmOverview());
                        intent.putExtra("vote_average", Double.toString(movies.get(position).getmVote_average()));
                        intent.putExtra("release_date", movies.get(position).getmRelease_date());
                        intent.putExtra("PositionMovie",movies.get(position));
                        context.startActivity(intent);
                    }
                }

            });
        }
    }

    public MyAdapter(Context context , List<Movies> movies){
        this.movies=movies;
        this.context=context;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

}
