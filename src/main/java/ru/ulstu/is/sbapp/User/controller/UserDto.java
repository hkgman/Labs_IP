package ru.ulstu.is.sbapp.User.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;
import ru.ulstu.is.sbapp.User.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;
    private String login;
    private String email;
    private String password;
    private UserRole role;

    private List<Post> posts = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public UserDto(){}
    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Post> getPosts()
    {
        return  posts;
    }
    public List<Comment> getComments()
    {
        return  comments;
    }

    public UserRole getRole() {
        return role;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
