package br.com.githubsummaryapp.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "githubsummary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SEARCH_HISTORY=
            " CREATE TABLE IF NOT EXISTS search_history ( " +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    " user TEXT NOT NULL"+
                    ");";

    private static final String TABLE_USER =
            " CREATE TABLE IF NOT EXISTS favorite_user ( " +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    " login TEXT," +
                    " avatar_url TEXT, " +
                    " url TEXT, " +
                    " html_url TEXT, " +
                    " name TEXT, " +
                    " company TEXT, " +
                    " blog TEXT, " +
                    " location TEXT, " +
                    " email TEXT, " +
                    " bio TEXT, " +
                    " public_repos INTEGER, " +
                    " public_gists INTEGER, " +
                    " followers INTEGER, " +
                    " following INTEGER  " +
                    ");";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_SEARCH_HISTORY);
        sqLiteDatabase.execSQL(TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // currently not used
    }
}