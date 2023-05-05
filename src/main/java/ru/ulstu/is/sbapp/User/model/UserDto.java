package ru.ulstu.is.sbapp.User.model;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;

    private String login;

    private String email;

    private UserRole role;

    private String password;

    private List<Post> posts = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public UserDto(){}
    public UserDto(User user) {
        this.id=user.getId();
        this.login = user.getLogin();
        this.email= user.getEmail();
        this.role=user.getRole();
        this.password=user.getPassword();
    }
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword(){
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
    public void setEmail(String email)
    {
        this.email=email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
