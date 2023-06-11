package com.example.h071211082finalmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.h071211082finalmobile.Database.MovieDatabaseHelper;
import com.example.h071211082finalmobile.Database.TvDatabaseHelper;
import com.example.h071211082finalmobile.Model.MovieModel;
import com.example.h071211082finalmobile.Model.TvModel;
import com.example.h071211082finalmobile.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra-movie";
    public static final String EXTRA_TV = "extra-tv";

    private ActivityDetailsBinding binding;
    private MovieModel movieModel;
    private TvModel tvModel;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the movie or TV show model from the intent
        movieModel = getIntent().getParcelableExtra(EXTRA_MOVIE);
        tvModel = getIntent().getParcelableExtra(EXTRA_TV);

        // Set the data to the views
        if (tvModel != null) {
            binding.tvNamaFilm.setText(tvModel.getOriginal_name());
            binding.tvDate.setText(tvModel.getFirst_air_date());
            binding.tvOverview.setText(tvModel.getOverview());
            binding.tvRate.setText(tvModel.getVote_average());
            binding.ivType.setImageResource(R.drawable.baseline_tv_24);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + tvModel.getPoster_path()).into(binding.ivPosterPath);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + tvModel.getBackdrop_path()).into(binding.ivBackdropPath);
        } else if (movieModel != null) {
            binding.tvNamaFilm.setText(movieModel.getOriginal_title());
            binding.tvDate.setText(movieModel.getRelease_date());
            binding.tvOverview.setText(movieModel.getOverview());
            binding.tvRate.setText(movieModel.getVote_average());
            binding.ivType.setImageResource(R.drawable.baseline_movie_24);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path()).into(binding.ivPosterPath);
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movieModel.getBackdrop_path()).into(binding.ivBackdropPath);
        }

        // Set click listener for the favorite button
        binding.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    removeFromFavorites();
                } else {
                    addToFavorites();
                }
            }
        });

        // Check if the movie or TV show is already in favorites
        checkIfFavorite();
    }

    private void checkIfFavorite() {
        if (tvModel != null) {
            TvDatabaseHelper databaseHelper = TvDatabaseHelper.getInstance(this);
            isFavorite = databaseHelper.isTvShowFavorite(tvModel.getId());
        } else if (movieModel != null) {
            MovieDatabaseHelper databaseHelper = MovieDatabaseHelper.getInstance(this);
            isFavorite = databaseHelper.isMovieFavorite(movieModel.getId());
        }

        // Update the favorite button's appearance based on the favorite status
        updateFavoriteButton();
    }

    private void addToFavorites() {
        if (tvModel != null) {
            TvDatabaseHelper databaseHelper = TvDatabaseHelper.getInstance(this);
            long result = databaseHelper.addTvShow(tvModel);
            if (result != -1) {
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                isFavorite = true;
                updateFavoriteButton();
            }
        } else if (movieModel != null) {
            MovieDatabaseHelper databaseHelper = MovieDatabaseHelper.getInstance(this);
            long result = databaseHelper.addMovie(movieModel);
            if (result != -1) {
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                isFavorite = true;
                updateFavoriteButton();
            }
        }
    }

    private void removeFromFavorites() {
        if (tvModel != null) {
            TvDatabaseHelper databaseHelper = TvDatabaseHelper.getInstance(this);
            int result = databaseHelper.removeTvShow(tvModel.getId());
            if (result > 0) {
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                isFavorite = false;
                updateFavoriteButton();
            }
        } else if (movieModel != null) {
            MovieDatabaseHelper databaseHelper = MovieDatabaseHelper.getInstance(this);
            int result = databaseHelper.removeMovie(movieModel.getId());
            if (result > 0) {
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                isFavorite = false;
                updateFavoriteButton();
            }
        }
    }

    private void updateFavoriteButton() {
        // Change the favorite button's image based on the favorite status
        int favoriteIcon = isFavorite ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24;
        binding.ivFavorite.setImageResource(favoriteIcon);
    }
}
