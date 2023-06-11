package com.example.h071211082finalmobile.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071211082finalmobile.DetailsActivity;
import com.example.h071211082finalmobile.Model.MovieModel;
import com.example.h071211082finalmobile.databinding.ItemCardBinding;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final List<MovieModel> movieModelList;

    public MovieAdapter(List<MovieModel> movieModel) {this.movieModelList = movieModel;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movieModel =movieModelList.get(position);
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path()).into(holder.binding.imageView);
        holder.binding.tvNamaFilm.setText(movieModel.getOriginal_title());
        holder.binding.tvTahun.setText(movieModel.getRelease_date());
        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(v.getContext(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_MOVIE, movieModel);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {return movieModelList.size();}

    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemCardBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemCardBinding.bind(itemView);
        }
    }
}
