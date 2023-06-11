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
import com.example.h071211082finalmobile.Model.TvModel;
import com.example.h071211082finalmobile.R;

import java.util.List;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.ViewHolder> {

    private List<TvModel> tvModelList;
    private Context context;

    public FavTvAdapter(List<TvModel> tvModelList, Context context) {
        this.tvModelList = tvModelList;
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
        TvModel tvModel = tvModelList.get(position);

        // Set the movie details to the views in the ViewHolder
        holder.titleTextView.setText(tvModel.getOriginal_name());
        holder.releaseDateTextView.setText(tvModel.getFirst_air_date());
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + tvModel.getPoster_path()).into(holder.categoriesImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_TV, tvModel);
                v.getContext().startActivity(intent);
            }
        });


        // You can also set click listeners or perform additional operations as needed
    }

    @Override
    public int getItemCount() {
        return tvModelList.size();
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
