package ru.ulstu.is.sbapp.Comment.controller;

import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

public class CommentDto {
    private Long id;
    private String Text;
    private Post post;
    private User user;
    public CommentDto(String text)
    {
        this.Text=text;
    }
    public Long getId()
    {
        return id;
    }
    public String getText() {return  Text;}

    public User getUser()
    {
        return user;
    }

    public Post getPost()
    {
        return post;
    }

}
