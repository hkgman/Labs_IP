package ru.ulstu.is.sbapp.Comment.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

public class CommentDto {
    private Long id;
    private String Text;
    private String userName;
    public CommentDto(){}
    public CommentDto(Comment comment)
    {
        this.id= comment.getId();
        this.Text=comment.getText();
        this.userName=comment.getUser().getLogin();
    }
    public Long getId()
    {
        return id;
    }
    public String getText() {return  Text;}

    public String getUser()
    {
        return userName;
    }


}
