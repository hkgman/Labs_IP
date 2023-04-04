package ru.ulstu.is.sbapp.User.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<Post> posts = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public UserDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
    }
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public List<Post> getPosts()
    {
        return  posts;
    }
    public List<Comment> getComments()
    {
        return  comments;
    }


    public String getEmail()
    {
        return email;
    }
}
