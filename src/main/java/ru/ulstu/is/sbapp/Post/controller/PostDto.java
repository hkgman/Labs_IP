package ru.ulstu.is.sbapp.Post.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private Long id;

    private String Heading;

    private String Content;

    private User user;

    private List<Comment> comments = new ArrayList<>();

    public PostDto(String Heading, String Content)
    {
        this.Heading = Heading;
        this.Content = Content;
    }

    public Long getId()
    {
        return id;
    }
    public String getHeading()
    {
        return Heading;
    }
    public String getContent()
    {
        return Content;
    }
    public User getUser()
    {
        return user;
    }
    public List<Comment> getComments()
    {
        return comments;
    }
}
