package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.popularmovies.Movies.Trialaer;

import java.util.List;

public class TrialaerAdapter extends RecyclerView.Adapter<TrialaerAdapter.MyViewHolderTrialaer> {




    List<Trialaer> trialaers ;
    Context context ;
    public TrialaerAdapter(List<Trialaer> trialaers, Context context) {
        this.trialaers = trialaers;
        this.context = context;
        notifyDataSetChanged();

    }

    public void setTrialaers(List<Trialaer> trialaers) {
        this.trialaers = trialaers;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TrialaerAdapter.MyViewHolderTrialaer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_trailer,parent,false);
        return new MyViewHolderTrialaer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrialaerAdapter.MyViewHolderTrialaer holder, int position) {
                holder.titel.setText(trialaers.get(holder.getAdapterPosition()).getmName_Trailaer());

    }

    @Override
    public int getItemCount() {
        if(trialaers==null)
            return 0;
        else
            return trialaers.size() ;
    }

    public class MyViewHolderTrialaer extends RecyclerView.ViewHolder {
        TextView titel ;
        int position;

        public MyViewHolderTrialaer(@NonNull View itemView) {
            super(itemView);
            titel=itemView.findViewById(R.id.name_trialaer);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position=getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        String KeyVedio = trialaers.get(position).getMkey();
                        Intent intent = new Intent(Intent.ACTION_VIEW );
                        intent.setData(Uri.parse("http://www.youtube.com/watch?v="+KeyVedio));
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
