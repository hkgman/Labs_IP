package ru.ulstu.is.sbapp.User.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tab_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Login can't be null or empty")
    @Size(min = 3, max = 64, message = "Incorrect login length")
    private String login;

    @NotBlank(message = "email cannot be null")
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Incorrect email value")
    private String email;

    @Column(nullable = false, length = 64)
    @NotBlank
    @Size(min = 6, max = 64)
    private String password;

    private UserRole role;

    @OneToMany(mappedBy ="user",cascade = {CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<Post> posts =new ArrayList<>();

    @OneToMany(mappedBy ="user",cascade = {CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<Comment> comments =new ArrayList<>();

    public User() {
    }
    public User(String login,String email, String password) {
        this(login,email, password, UserRole.USER);
    }
    public User(String login,String email,String password,UserRole role) {
        this.login=login;
        this.email=email;
        this.password=password;
        this.role=role;
    }
    public User(UserSignupDto userSignupDto) {
        this.login = userSignupDto.getLogin();
        this.email = userSignupDto.getEmail();
        this.password = userSignupDto.getPassword();
        this.role = UserRole.USER;
    }

    public Long getId() {
        return id;
    }

    public List<Post> getPosts()
    {
        return  posts;
    }
    public List<Comment> getComments()
    {
        return  comments;
    }
    public void addNewPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public UserRole getRole() {
        return role;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +'\''+
                '}';
    }

}
