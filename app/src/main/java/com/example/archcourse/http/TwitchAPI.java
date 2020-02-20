package com.example.archcourse.http;

import com.example.archcourse.http.twitch.Streams2;
import com.example.archcourse.http.twitch.Twitch;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames(@Header("Client-Id") String clientId);

    @GET("games/top")
    Observable<Twitch> getTopGamesObsevable(@Header("Client-Id") String clientId);

    @GET("games")
    Observable<Twitch> getGameById(@Header("Client-Id") String clientId, @Query("id") int id);

    @GET("streams")
    Observable<Streams2> getStreams(@Header("Client-Id") String clientId);

}
