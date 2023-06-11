package com.example.h071211082finalmobile.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.h071211082finalmobile.Adapter.FavMovieAdapter;
import com.example.h071211082finalmobile.Adapter.FavTvAdapter;
import com.example.h071211082finalmobile.Database.MovieDatabaseHelper;
import com.example.h071211082finalmobile.Database.TvDatabaseHelper;
import com.example.h071211082finalmobile.Model.MovieModel;
import com.example.h071211082finalmobile.Model.TvModel;
import com.example.h071211082finalmobile.R;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2;
    private FavMovieAdapter favMovieAdapter;
    private FavTvAdapter favTvAdapter;
    private List<MovieModel> movieList;
    private List<TvModel> tvList;
    private MovieDatabaseHelper dbHelper;
    private TvDatabaseHelper dbHelper2;
    TextView tv1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.rvMovie);
        recyclerView2 = view.findViewById(R.id.rvTv);
        tv1 = view.findViewById(R.id.text1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = MovieDatabaseHelper.getInstance(view.getContext());
        dbHelper2 = TvDatabaseHelper.getInstance(view.getContext());
        movieList = dbHelper.getAllMovies();
        tvList = dbHelper2.getAllTvShows();

        favMovieAdapter = new FavMovieAdapter(movieList, getContext());
        favTvAdapter = new FavTvAdapter(tvList, getContext());
        recyclerView.setAdapter(favMovieAdapter);
        recyclerView2.setAdapter(favTvAdapter);
        if (movieList.size()+tvList.size()!=0){
            tv1.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the movie list from the database
        movieList.clear();
        tvList.clear();
        movieList.addAll(dbHelper.getAllMovies());
        tvList.addAll(dbHelper2.getAllTvShows());
        favMovieAdapter.notifyDataSetChanged();
        favTvAdapter.notifyDataSetChanged();
        if (movieList.size()+tvList.size()!=0){
            tv1.setVisibility(View.GONE);
        }
        else {
            tv1.setVisibility(View.VISIBLE);
        }
    }
}
