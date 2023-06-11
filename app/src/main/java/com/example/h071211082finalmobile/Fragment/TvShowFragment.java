package com.example.h071211082finalmobile.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.h071211082finalmobile.Adapter.TvAdapter;
import com.example.h071211082finalmobile.Model.TvModel;
import com.example.h071211082finalmobile.Model.TvResponse;
import com.example.h071211082finalmobile.Networking.ApiConfig;
import com.example.h071211082finalmobile.databinding.FragmentTvShowBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowFragment extends Fragment {

    private FragmentTvShowBinding binding;

    private TvAdapter tvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTvShowBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvTv.setHasFixedSize(true);
        binding.rvTv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ApiConfig.getApiService().getListTv().enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        List<TvModel> tvModels = response.body().getTvModels();
                        tvAdapter = new TvAdapter(tvModels);
                        binding.rvTv.setAdapter(tvAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });
    }
}