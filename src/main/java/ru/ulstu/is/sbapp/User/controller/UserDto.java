package ru.ulstu.is.sbapp.User.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private List<Post> posts = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public UserDto(){}
    public UserDto(User user) {
        this.id=user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email= user.getEmail();
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
