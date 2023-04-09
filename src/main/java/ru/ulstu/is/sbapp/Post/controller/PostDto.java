package ru.ulstu.is.sbapp.Post.controller;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private Long id;

    private String heading;

    private String content;


    private String image;


    public PostDto(){}
    public PostDto(Post post)
    {
        this.id= post.getId();
        this.heading = post.getHeading();
        this.content = post.getContent();
        this.image = new String(post.getImage(), StandardCharsets.UTF_8);
    }


    public Long getId()
    {
        return id;
    }
    public String getHeading()
    {
        return heading;
    }
    public String getContent()
    {
        return content;
    }
    public String getImage() {
        return image;
    }
}
