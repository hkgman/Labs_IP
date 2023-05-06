package ru.ulstu.is.sbapp.User.service;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String login) {
        super(String.format("User '%s' already exists", login));
    }
}