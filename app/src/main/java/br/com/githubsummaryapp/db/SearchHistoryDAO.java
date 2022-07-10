package br.com.githubsummaryapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.githubsummaryapp.domain.SearchHistory;

public class SearchHistoryDAO extends DAO<SearchHistory> {

    public SearchHistoryDAO(Context context){
        super(context);
    }

    @Override
    public void save(SearchHistory entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("user", entity.getUser());

        database.insert("search_history", null, contentValues);

        database.close();
    }

    @Override
    public void update(SearchHistory entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("user", entity.getUser());
        String[] params = {entity.getId().toString()};
        database.update("search_history", contentValues,
                "id = ?", params);

        database.close();
    }

    @Override
    public void delete(SearchHistory entity) {
        SQLiteDatabase database = openToWrite();

        String[] params = {entity.getId().toString()};
        database.delete("search_history", " id = ? ", params);

        database.close();
    }

    @Override
    public List<SearchHistory> listAll() {
        SQLiteDatabase database = openToRead();
        List<SearchHistory> searchHistories = new ArrayList<>();

        String sql = " SELECT * FROM search_history ORDER BY id DESC";

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String user = cursor.getString(
                    cursor.getColumnIndexOrThrow("user"));

            SearchHistory searchHistory = new SearchHistory(id, user);
            searchHistories.add(searchHistory);
        }

        cursor.close();
        database.close();

        return searchHistories;
    }

    public SearchHistory findById(Integer searchHistoryId) {
        SQLiteDatabase database = openToRead();
        SearchHistory searchHistory = null;

        String sql = " SELECT * FROM search_history WHERE id = ?; ";

        String[] params = {searchHistoryId.toString()};

        Cursor cursor = database.rawQuery(sql, params);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String user = cursor.getString(
                    cursor.getColumnIndexOrThrow("user"));

            searchHistory = new SearchHistory(id, user);
        }

        cursor.close();
        database.close();

        return searchHistory;
    }
}

