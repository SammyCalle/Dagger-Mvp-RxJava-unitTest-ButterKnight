package com.example.archcourse;

import com.example.archcourse.login.LoginActivity;
import com.example.archcourse.login.LoginActivityMVP;
import com.example.archcourse.login.LoginActivityPresenter;
import com.example.archcourse.login.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PresenterUnitTest {

    LoginActivityPresenter presenter;
    User user;

    LoginActivityMVP.View mockedview;
    LoginActivityMVP.Model mockedmodel;

    @Before
    public void init(){

        mockedmodel = mock(LoginActivityMVP.Model.class);
        mockedview = mock(LoginActivityMVP.View.class);

        user = new User("Don","Joe");

//        when(mockedmodel.getUser()).thenReturn(user);
        when(mockedview.getFirstName()).thenReturn("Sax");
        when(mockedview.getLastName()).thenReturn("Max");

        presenter = new LoginActivityPresenter(mockedmodel);
        presenter.setView(mockedview);

    }

    @Test
    public void noExistInterationWithView(){

        presenter.getCurrentUser();
        verify(mockedview, times(1)).showUserNotAvailable();
    }

    @Test
    public void loadUserFromTheRepoWhenValidUserIsPresent(){

        when(mockedmodel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        verify(mockedmodel,times(1)).getUser();

        verify(mockedview,times(1)).setFirstName("Don");
        verify(mockedview,times(1)).setLastName("Joe");
        verify(mockedview,never()).showUserNotAvailable();
    }

    @Test
    public void verifyErrorMessageOnNullUser(){

        when(mockedmodel.getUser()).thenReturn(null);

        presenter.getCurrentUser();

        verify(mockedmodel,times(1)).getUser();

        verify(mockedview,never()).setFirstName("Don");
        verify(mockedview,never()).setLastName("Joe");
        verify(mockedview,times(1)).showUserNotAvailable();
    }

    @Test
    public void createErrorMessageIfAnyfieldIsEmpty(){
        //Primer Caso , firts name vacio
        when(mockedview.getFirstName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockedview,times(1)).getFirstName();
        verify(mockedview,never()).getLastName();
        verify(mockedview,times(1)).showInputError();

        //Segundo caso , last name vacio

        when(mockedview.getFirstName()).thenReturn("Don");
        when(mockedview.getLastName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockedview,times(2)).getFirstName();
        verify(mockedview,times(1)).getLastName();
        verify(mockedview,times(2)).showInputError();

    }

    @Test
    public void saveValidUser(){
        when(mockedview.getFirstName()).thenReturn("Sax");
        when(mockedview.getLastName()).thenReturn("Max");

        presenter.loginButtonClicked();

        verify(mockedview,times(2)).getFirstName();
        verify(mockedview,times(2)).getLastName();

        verify(mockedmodel,times(1)).createUser("Sax","Max");
        verify(mockedview,times(1)).showUserSaved();
    }
}
