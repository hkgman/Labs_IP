package ru.ulstu.is.sbapp.Comment.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date DateOfAdd;

    private String Text;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Comment()
    {

    }

    public Comment(Date DateOfAdd,String text)
    {
        this.DateOfAdd=DateOfAdd;
        this.Text=text;
    }
    public Long getId()
    {
        return id;
    }
    public Date getDateOfAdd()
    {
        return  DateOfAdd;
    }
    public String getText() {return  Text;}
    public void setDateOfAdd(Date DateOfAdd)
    {
        this.DateOfAdd=DateOfAdd;
    }
    public void setText(String text){this.Text=text;}
    public User getUser()
    {
        return user;
    }
    public Post getPost()
    {
        return post;
    }
    public void setUser(User user) {
        this.user = user;
        if (!user.getComments().contains(this)) {
            user.addNewComment(this);
        }
    }
    public void deleteUser() {
        if (user.getComments().contains(this)) {
            user.deleteComment(this);
        }
        this.user = null;
    }
    public void setPost(Post post) {
        this.post = post;
        if (!post.getComments().contains(this)) {
            post.addNewComment(this);
        }
    }
    public void deletePost() {
        if (post.getComments().contains(this)) {
            post.deleteComment(this);
        }
        this.user = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", DateOfAdd='" + DateOfAdd + '\'' +
                ", Text='" + Text + '\'' +
                '}';
    }

}
