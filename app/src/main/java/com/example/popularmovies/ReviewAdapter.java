package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.Movies.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolderReview> {



    List<Review> reviews;
    Context context;
    public ReviewAdapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
        notifyDataSetChanged();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public ReviewAdapter.MyViewHolderReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_review,parent,false);
        return new MyViewHolderReview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolderReview holder, int position) {
                    holder.author.setText(reviews.get(position).getmAuthor());
                    holder.review.setText(reviews.get(position).getmContent());
    }

    @Override
    public int getItemCount() {
        if(reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    public class MyViewHolderReview extends RecyclerView.ViewHolder {

        TextView author ;
        TextView review ;


        public MyViewHolderReview(@NonNull View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.text_auther);
            review=itemView.findViewById(R.id.text_review);
        }
    }
}
