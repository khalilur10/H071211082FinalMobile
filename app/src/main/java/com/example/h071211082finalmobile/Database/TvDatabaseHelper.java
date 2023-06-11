package com.example.h071211082finalmobile.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.h071211082finalmobile.Model.TvModel;

import java.util.ArrayList;
import java.util.List;

public class TvDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tvShowsDatabase";
    private static final int DATABASE_VERSION = 1;

    private static TvDatabaseHelper instance;

    private TvDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized TvDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new TvDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TV_SHOWS_TABLE = "CREATE TABLE tvShows " +
                "(id INTEGER PRIMARY KEY, " +
                "backdrop_path TEXT, " +
                "original_name TEXT, " +
                "first_air_date TEXT, " +
                "overview TEXT, " +
                "poster_path TEXT, " +
                "vote_average TEXT)";
        db.execSQL(CREATE_TV_SHOWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tvShows");
        onCreate(db);
    }

    public long addTvShow(TvModel tvModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", tvModel.getId());
        values.put("backdrop_path", tvModel.getBackdrop_path());
        values.put("original_name", tvModel.getOriginal_name());
        values.put("first_air_date", tvModel.getFirst_air_date());
        values.put("overview", tvModel.getOverview());
        values.put("poster_path", tvModel.getPoster_path());
        values.put("vote_average", tvModel.getVote_average());
        return db.insert("tvShows", null, values);
    }

    public int removeTvShow(int tvShowId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("tvShows", "id = ?", new String[]{String.valueOf(tvShowId)});
    }

    public boolean isTvShowFavorite(int tvShowId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM tvShows WHERE id = " + tvShowId;
        Cursor cursor = db.rawQuery(query, null);
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        return isFavorite;
    }

    @SuppressLint("Range")
    public List<TvModel> getAllTvShows() {
        List<TvModel> tvShows = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tvShows", null);
        if (cursor.moveToFirst()) {
            do {
                TvModel tvModel = new TvModel();
                tvModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tvModel.setBackdrop_path(cursor.getString(cursor.getColumnIndex("backdrop_path")));
                tvModel.setOriginal_name(cursor.getString(cursor.getColumnIndex("original_name")));
                tvModel.setFirst_air_date(cursor.getString(cursor.getColumnIndex("first_air_date")));
                tvModel.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                tvModel.setPoster_path(cursor.getString(cursor.getColumnIndex("poster_path")));
                tvModel.setVote_average(cursor.getString(cursor.getColumnIndex("vote_average")));
                tvShows.add(tvModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tvShows;
    }
}

