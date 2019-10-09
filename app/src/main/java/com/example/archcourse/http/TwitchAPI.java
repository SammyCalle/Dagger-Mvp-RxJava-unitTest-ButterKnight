package com.example.archcourse.http;

import com.example.archcourse.http.twitch.Twitch;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames();
}
