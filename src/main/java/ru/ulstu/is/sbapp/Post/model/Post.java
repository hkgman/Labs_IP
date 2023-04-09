package ru.ulstu.is.sbapp.Post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.controller.PostDto;
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
    @NotBlank(message = "heading cannot be null")
    private String heading;

    @NotBlank(message = "content cannot be null")
    private String content;

    @Lob
    private byte[] image;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments=new ArrayList<>();

    public Post(){}
    public Post(String Heading, String Content,byte[] image)
    {
        this.heading = Heading;
        this.content = Content;
        this.image=image;
    }
    public Post(PostDto postDto) {
        this.heading = postDto.getHeading();
        this.content = postDto.getContent();
        this.image = postDto.getImage().getBytes();
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
    public void setHeading(String Heading){
        this.heading =Heading;
    }
    public void setContent(String Content)
    {
        this.content = Content;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser()
    {
        return user;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
        return "Post{" +
                "id=" + id +
                ", heading='" + heading + '\'' +
                ", content ='" + content + '\'' +
                '}';
    }
}
