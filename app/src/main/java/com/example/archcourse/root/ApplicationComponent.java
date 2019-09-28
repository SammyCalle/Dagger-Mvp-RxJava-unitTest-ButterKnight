package com.example.archcourse.root;

import com.example.archcourse.login.LoginActivity;
import com.example.archcourse.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity loginActivity);

}
