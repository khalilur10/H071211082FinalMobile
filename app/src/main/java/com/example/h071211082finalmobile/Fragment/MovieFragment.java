package com.example.h071211082finalmobile.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.h071211082finalmobile.Adapter.MovieAdapter;
import com.example.h071211082finalmobile.Model.MovieModel;
import com.example.h071211082finalmobile.Model.MovieResponse;
import com.example.h071211082finalmobile.Networking.ApiConfig;
import com.example.h071211082finalmobile.R;
import com.example.h071211082finalmobile.databinding.FragmentMovieBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private FragmentMovieBinding binding;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private TextView connectionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = getActivity().findViewById(R.id.progresbar);
        connectionText = getActivity().findViewById(R.id.connection);

        binding.rvMovie.setHasFixedSize(true);
        binding.rvMovie.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (isNetworkConnected()) {
            loadData();
        } else {
            connectionText.setVisibility(View.VISIBLE);
            connectionText.setText("There is no internet connection");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        ApiConfig.getApiService().getListMovie().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<MovieModel> movieModel = response.body().getMovieModels();
                        for (int i = 0; i < movieModel.size(); i++) {
                        }
                        movieAdapter = new MovieAdapter(movieModel);
                        binding.rvMovie.setAdapter(movieAdapter);
                        progressBar.setVisibility(View.GONE);
                        connectionText.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("aduh", "ude'eh");
                progressBar.setVisibility(View.GONE);
                connectionText.setVisibility(View.VISIBLE);
                connectionText.setText("Failed to load data");
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
