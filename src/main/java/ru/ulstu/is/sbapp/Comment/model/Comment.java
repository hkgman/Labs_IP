package ru.ulstu.is.sbapp.Comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Text cannot be null")
    private String Text;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private User user;

    public Comment()
    {

    }

    public Comment(String text)
    {
        this.Text=text;
    }
    public Long getId()
    {
        return id;
    }
    public String getText() {return  Text;}
    public void setText(String text){this.Text=text;}
    public User getUser()
    {
        return user;
    }
    public Post getPost()
    {
        return post;
    }
    public void setPost(Post post, User user){
        if(post!=null)
        {
            post.getComments().add(this);
            this.post = post;

            this.user = user;
        }

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
                ", Text='" + Text + '\'' +
                '}';
    }

}
