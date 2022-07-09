package br.com.githubsummaryapp.service;

import br.com.githubsummaryapp.domain.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") String id);
}
