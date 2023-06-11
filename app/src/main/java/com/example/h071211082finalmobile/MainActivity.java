package com.example.h071211082finalmobile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.h071211082finalmobile.Fragment.FavoritesFragment;
import com.example.h071211082finalmobile.Fragment.MovieFragment;
import com.example.h071211082finalmobile.Fragment.TvShowFragment;
import com.example.h071211082finalmobile.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;
    private MovieFragment moviesFragment = new MovieFragment();
    private TvShowFragment tvShowsFragment = new TvShowFragment();
    private FavoritesFragment favoritesFragment = new FavoritesFragment();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();
        progressBar = binding.progresbar;

        Fragment fragment = fragmentManager.findFragmentByTag(MovieFragment.class.getSimpleName());

        if (!(fragment instanceof MovieFragment)) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, moviesFragment, MovieFragment.class.getSimpleName())
                    .commit();
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_movies:
                        showProgressBar();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, moviesFragment).commit();
                        binding.toolbarTitle.setText("Movies");
                        return true;
                    case R.id.action_tv_shows:
                        showProgressBar();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, tvShowsFragment).commit();
                        binding.toolbarTitle.setText("TV Shows");
                        return true;
                    case R.id.action_favorites:
                        showProgressBar();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, favoritesFragment).commit();
                        binding.toolbarTitle.setText("Favorites");
                        return true;
                }
                return false;
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}