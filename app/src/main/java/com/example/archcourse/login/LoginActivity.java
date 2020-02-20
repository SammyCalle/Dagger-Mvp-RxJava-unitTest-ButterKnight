package com.example.archcourse.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.archcourse.R;
import com.example.archcourse.http.TwitchAPI;
import com.example.archcourse.http.twitch.Data;
import com.example.archcourse.http.twitch.Game;
import com.example.archcourse.http.twitch.Streams2;
import com.example.archcourse.http.twitch.Twitch;
import com.example.archcourse.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View{

    @Inject
    LoginActivityMVP.Presenter presenter;

    @Inject
    TwitchAPI twitchAPI;

    EditText firstName, lastName;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getComponent().inject(this);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginButtonClicked();
            }
        });

//        List<Streams2> EspañolLista = new ArrayList<>();
//        EspañolLista = twitchAPI.getStreams("s0706njscdfxi5hv7xoplpimt8cul8")
//        twitchAPI.getStreams("s0706njscdfxi5hv7xoplpimt8cul8")
//                .flatMap(new Function<Streams2, Observable<Streams2>>() {
//                    @Override
//                    public Observable<Streams2> apply(Streams2 streams2) throws Exception {
//                        return Observable.fromArray(streams2);
//                    }
//                })
//                .flatMap(Observable.fromIterable(twitchAPI.getStreams("s0706njscdfxi5hv7xoplpimt8cul8")))
        twitchAPI.getStreams("s0706njscdfxi5hv7xoplpimt8cul8")
                .flatMap(new Function<Streams2, Observable<Data>>() {
                    @Override
                    public Observable<Data> apply(Streams2 streams2) throws Exception {
                        return Observable.fromIterable(streams2.getData());
                    }
                })
                .filter(new Predicate<Data>() {
                    @Override
                    public boolean test(Data data) throws Exception {
                        return data.getLanguage().contains("en")&& Integer.parseInt(data.getViewer_count()) > 10000;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data data) {
//                            System.out.println("Inicio de prueba GET + Streams");
//                            System.out.println(data.getTitle());
//                            System.out.println(data.getLanguage());
//                            System.out.println(data.getViewer_count());
//                            System.out.println(data.getUser_name());
//                            System.out.println("Fin de prueba GET + Streams");
                        getGamesbyId(data.getGame_id());
                        }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        Observable<Streams2> call = twitchAPI.getStreams("s0706njscdfxi5hv7xoplpimt8cul8");
//
//            @Override
//            public void onResponse(Call<Streams2> call, Response<Streams2> response) {
//                List<Data> streams = response.body().getData();
//                List<String> tags = response.body().getTag_ids();
//                for (Data stream: streams){
//                    System.out.println("Inicio de prueba GET + Streams");
//                    System.out.println(stream.getTitle());
//                    System.out.println(stream.getLanguage());
//                    System.out.println(stream.getViewer_count());
//                    System.out.println(tags);
//                    System.out.println("Fin de prueba GET + Streams");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Streams2> call, Throwable t) {
//
//            }
//        });

       /* Call<Twitch> call = twitchAPI.getGameById("s0706njscdfxi5hv7xoplpimt8cul8",493057);
        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Game> topGames = response.body().getData();
                for (Game game: topGames){

                }

            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });

        twitchAPI.getTopGamesObsevable("s0706njscdfxi5hv7xoplpimt8cul8")
                .flatMap(new Function<Twitch, Observable<Game>>() {
                    @Override
                    public Observable<Game> apply(Twitch twitch) throws Exception {
                        return Observable.fromIterable(twitch.getData());
                    }
                })
                .flatMap(new Function<Game, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Game game) throws Exception {
                        return Observable.just(game.getName());
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.startsWith("W")||s.startsWith("w");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("RxJava says: "+s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    private void getGamesbyId(String game_id) {
        twitchAPI.getGameById("s0706njscdfxi5hv7xoplpimt8cul8",Integer.parseInt(game_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Twitch>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Twitch twitch) {
                        List<Game> games = twitch.getData();
                        for (Game juegos : games){
                            System.out.println("El Nombre del juego es");
                            System.out.println(juegos.getName());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getFirstName() {
        return this.firstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return this.lastName.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this,"Error, el usuario no esta disponible", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showInputError() {
        Toast.makeText(this,"Error, ni el nombre ni el apellido pueden estar vacios", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSaved() {
        Toast.makeText(this,"!Usuario guardado correctgamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }
}
