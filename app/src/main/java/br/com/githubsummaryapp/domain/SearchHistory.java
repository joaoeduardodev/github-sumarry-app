package br.com.githubsummaryapp.domain;

import java.io.Serializable;

public class SearchHistory implements Serializable {
    private Integer id;
    private String user;

    public SearchHistory(Integer id, String user){
        this.id = id;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String name) {
        this.user = name;
    }

    @Override
    public String toString() {
        return this.user;
    }
}
