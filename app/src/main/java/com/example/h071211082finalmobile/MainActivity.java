package com.example.h071211082finalmobile;

import android.os.Bundle;
import android.view.MenuItem;

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
    MovieFragment moviesFragment = new MovieFragment();
    TvShowFragment tvShowsFragment = new TvShowFragment();
    FavoritesFragment favoritesFragment = new FavoritesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();

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
                        fragmentManager.beginTransaction().replace(R.id.frame_container, moviesFragment).commit();
                        binding.toolbarTitle.setText("Movies");
                        return true;
                    case R.id.action_tv_shows:
                        fragmentManager.beginTransaction().replace(R.id.frame_container, tvShowsFragment).commit();
                        binding.toolbarTitle.setText("TV Shows");
                        return true;
                    case R.id.action_favorites:
                        fragmentManager.beginTransaction().replace(R.id.frame_container, favoritesFragment).commit();
                        binding.toolbarTitle.setText("Favorites");
                        return true;
                }
                return false;
            }
        });
    }
}
