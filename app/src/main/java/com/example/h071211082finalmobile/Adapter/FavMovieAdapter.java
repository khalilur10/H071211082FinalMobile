package com.example.h071211082finalmobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071211082finalmobile.DetailsActivity;
import com.example.h071211082finalmobile.Model.MovieModel;
import com.example.h071211082finalmobile.R;

import java.util.List;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ViewHolder> {

    private List<MovieModel> movieList;
    private Context context;

    public FavMovieAdapter(List<MovieModel> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorites, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movie = movieList.get(position);

        // Set the movie details to the views in the ViewHolder
        holder.titleTextView.setText(movie.getOriginal_title());
        holder.releaseDateTextView.setText(movie.getRelease_date());
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(holder.categoriesImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_MOVIE, movie);
                v.getContext().startActivity(intent);
            }
        });


        // You can also set click listeners or perform additional operations as needed
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView releaseDateTextView;
        private ImageView categoriesImageView;
        private CardView cvMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tittleMovie);
            releaseDateTextView = itemView.findViewById(R.id.realeseDateMovie);
            categoriesImageView = itemView.findViewById(R.id.movieCategories);
            cvMovie = itemView.findViewById(R.id.cvMovie);
        }
    }
}
