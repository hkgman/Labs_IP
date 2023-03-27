package ru.ulstu.is.sbapp.Post.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String Heading;

    private String Content;



    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private User user;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private List<Comment> comments=new ArrayList<>();

    public Post(){}

    public Post(String Heading, String Content)
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
    public void setHeading(String Heading){
        this.Heading =Heading;
    }
    public void setContent(String Content)
    {
        this.Content = Content;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser()
    {
        return user;
    }


    public void deleteUser() {
        this.user = null;
    }
    public List<Comment> getComments()
    {
        return comments;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Technique{" +
                "id=" + id +
                ", Heading='" + Heading + '\'' +
                ", Content ='" + Content + '\'' +
                '}';
    }
}
