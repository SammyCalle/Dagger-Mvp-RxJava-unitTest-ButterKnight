package com.example.archcourse.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.archcourse.R;
import com.example.archcourse.http.TwitchAPI;
import com.example.archcourse.http.twitch.Game;
import com.example.archcourse.http.twitch.Twitch;
import com.example.archcourse.root.App;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Call<Twitch> call = twitchAPI.getTopGames("s0706njscdfxi5hv7xoplpimt8cul8");
        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Game> topGames = response.body().getData();
                for (Game game: topGames){
                    System.out.println(game.getName());
                }

            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
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
