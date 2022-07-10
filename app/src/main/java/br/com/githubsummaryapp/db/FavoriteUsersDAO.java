package br.com.githubsummaryapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.githubsummaryapp.domain.FavoriteUsers;
import br.com.githubsummaryapp.domain.SearchHistory;
import br.com.githubsummaryapp.domain.User;

public class FavoriteUsersDAO extends DAO<FavoriteUsers> {

    public FavoriteUsersDAO(Context context){
        super(context);
    }

    @Override
    public void save(FavoriteUsers entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("login", entity.getLogin());
        contentValues.put("avatar_url", entity.getAvatar_url());
        contentValues.put("url", entity.getUrl());
        contentValues.put("html_url", entity.getHtml_url());
        contentValues.put("name", entity.getName());
        contentValues.put("company", entity.getCompany());
        contentValues.put("blog", entity.getBlog());
        contentValues.put("location", entity.getLocation());
        contentValues.put("email", entity.getEmail());
        contentValues.put("bio", entity.getBio());
        contentValues.put("public_repos", entity.getPublic_repos());
        contentValues.put("public_gists", entity.getPublic_gists());
        contentValues.put("followers", entity.getFollowers());
        contentValues.put("following", entity.getFollowing());

        database.insert("favorite_user", null, contentValues);

        database.close();
    }

    @Override
    public void update(FavoriteUsers entity) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("login", entity.getLogin());
        String[] params = {entity.getId().toString()};
        database.update("favorite_user", contentValues,
                "id = ?", params);

        database.close();
    }

    @Override
    public void delete(FavoriteUsers entity) {
        SQLiteDatabase database = openToWrite();

        String[] params = {entity.getId().toString()};
        database.delete("favorite_user", " id = ? ", params);

        database.close();
    }

    @Override
    public List<FavoriteUsers> listAll() {
        SQLiteDatabase database = openToRead();
        List<FavoriteUsers> favoriteUsers = new ArrayList<>();

        String sql = " SELECT * FROM favorite_user ORDER BY id DESC";

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
            String avatar_url = cursor.getString(cursor.getColumnIndexOrThrow("avatar_url"));
            String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
            String html_url = cursor.getString(cursor.getColumnIndexOrThrow("html_url"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String company = cursor.getString(cursor.getColumnIndexOrThrow("company"));
            String blog = cursor.getString(cursor.getColumnIndexOrThrow("blog"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"));
            int public_repos = cursor.getInt(cursor.getColumnIndexOrThrow("public_repos"));
            int public_gists = cursor.getInt(cursor.getColumnIndexOrThrow("public_gists"));
            int followers = cursor.getInt(cursor.getColumnIndexOrThrow("followers"));
            int following = cursor.getInt(cursor.getColumnIndexOrThrow("following"));

            FavoriteUsers favoriteUser = new FavoriteUsers(id, login, avatar_url, url, html_url, name, company, blog, location, email, bio, public_repos, public_gists, followers, following);
            favoriteUsers.add(favoriteUser);
        }

        cursor.close();
        database.close();

        return favoriteUsers;
    }

    public FavoriteUsers findById(Integer favoritUserId) {
        SQLiteDatabase database = openToRead();
        FavoriteUsers favoriteUser = null;

        String sql = " SELECT * FROM favorite_user WHERE id = ?; ";

        String[] params = {favoritUserId.toString()};

        Cursor cursor = database.rawQuery(sql, params);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
            String avatar_url = cursor.getString(cursor.getColumnIndexOrThrow("avatar_url"));
            String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
            String html_url = cursor.getString(cursor.getColumnIndexOrThrow("html_url"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String company = cursor.getString(cursor.getColumnIndexOrThrow("company"));
            String blog = cursor.getString(cursor.getColumnIndexOrThrow("blog"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"));
            int public_repos = cursor.getInt(cursor.getColumnIndexOrThrow("public_repos"));
            int public_gists = cursor.getInt(cursor.getColumnIndexOrThrow("public_gists"));
            int followers = cursor.getInt(cursor.getColumnIndexOrThrow("followers"));
            int following = cursor.getInt(cursor.getColumnIndexOrThrow("following"));

            favoriteUser = new FavoriteUsers(id, login, avatar_url, url, html_url, name, company, blog, location, email, bio, public_repos, public_gists, followers, following);
        }

        cursor.close();
        database.close();

        return favoriteUser;
    }
}

