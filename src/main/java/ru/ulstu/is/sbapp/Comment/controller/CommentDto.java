package ru.ulstu.is.sbapp.Comment.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

public class CommentDto {
    private Long id;
    private String Text;
    private Post post;
    private User user;
    public CommentDto(Comment comment)
    {
        this.Text=comment.getText();
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
