package com.example.h071211082finalmobile.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.h071211082finalmobile.Model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movieDatabase";
    private static final int DATABASE_VERSION = 1;

    private static MovieDatabaseHelper instance;

    private MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MovieDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MovieDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE movies " +
                "(id INTEGER PRIMARY KEY, " +
                "backdrop_path TEXT, " +
                "original_title TEXT, " +
                "release_date TEXT, " +
                "overview TEXT, " +
                "poster_path TEXT, " +
                "vote_average TEXT)";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");
        onCreate(db);
    }

    public long addMovie(MovieModel movieModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", movieModel.getId());
        values.put("backdrop_path", movieModel.getBackdrop_path());
        values.put("original_title", movieModel.getOriginal_title());
        values.put("release_date", movieModel.getRelease_date());
        values.put("overview", movieModel.getOverview());
        values.put("poster_path", movieModel.getPoster_path());
        values.put("vote_average", movieModel.getVote_average());
        return db.insert("movies", null, values);
    }

    public int removeMovie(int movieId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("movies", "id = ?", new String[]{String.valueOf(movieId)});
    }

    public boolean isMovieFavorite(int movieId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM movies WHERE id = " + movieId;
        Cursor cursor = db.rawQuery(query, null);
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        return isFavorite;
    }

    @SuppressLint("Range")
    public List<MovieModel> getAllMovies() {
        List<MovieModel> movies = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies", null);
        if (cursor.moveToFirst()) {
            do {
                MovieModel movieModel = new MovieModel();
                movieModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                movieModel.setBackdrop_path(cursor.getString(cursor.getColumnIndex("backdrop_path")));
                movieModel.setOriginal_title(cursor.getString(cursor.getColumnIndex("original_title")));
                movieModel.setRelease_date(cursor.getString(cursor.getColumnIndex("release_date")));
                movieModel.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                movieModel.setPoster_path(cursor.getString(cursor.getColumnIndex("poster_path")));
                movieModel.setVote_average(cursor.getString(cursor.getColumnIndex("vote_average")));
                movies.add(movieModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movies;
    }
}
