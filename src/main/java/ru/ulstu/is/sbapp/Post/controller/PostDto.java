package ru.ulstu.is.sbapp.Post.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private Long id;

    private String Heading;

    private String Content;

    private User user;

    private String image;

    private List<Comment> comments = new ArrayList<>();

    public PostDto(Post post)
    {
        this.Heading = post.getHeading();
        this.Content = post.getContent();
        this.image = new String(post.getImage(), StandardCharsets.UTF_8);
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
    public String getImage() {
        return image;
    }
    public List<Comment> getComments()
    {
        return comments;
    }
}
