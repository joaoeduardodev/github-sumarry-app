package br.com.githubsummaryapp.config;

import br.com.githubsummaryapp.service.GitHubService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public GitHubService getGitHubService() {
        return this.retrofit.create(GitHubService.class);
    }

}