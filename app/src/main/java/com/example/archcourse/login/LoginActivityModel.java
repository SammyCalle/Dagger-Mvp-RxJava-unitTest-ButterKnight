package com.example.archcourse.login;

public class LoginActivityModel implements LoginActivityMVP.Model{

    private LoginRepository repository;

    public LoginActivityModel(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(String firstName, String lastName) {
        repository.saveUser(new User(firstName,lastName));
    }

    @Override
    public User getUser() {
        return repository.getUser();
    }
}
