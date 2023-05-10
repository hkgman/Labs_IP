package ru.ulstu.is.sbapp.Comment.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

public class CommentDto {
    private Long id;
    private String Text;
    private String userName;

    private Long userId;
    public CommentDto(){}
    public CommentDto(Comment comment)
    {
        this.id= comment.getId();
        this.Text=comment.getText();
        this.userName=comment.getUser().getLogin();
        this.userId=comment.getUser().getId();
    }
    public Long getId()
    {
        return id;
    }
    public String getText() {return  Text;}
    public Long getUserId(){return userId;}
    public String getUser()
    {
        return userName;
    }


}
