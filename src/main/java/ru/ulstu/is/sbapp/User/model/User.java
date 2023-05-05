package ru.ulstu.is.sbapp.User.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column()
    @NotBlank(message = "firstName cannot be null")
    private String firstName;
    @NotBlank(message = "lastName cannot be null")
    private String lastName;

    @Column(nullable = false, unique = true, length = 64)
    @NotBlank(message = "email cannot be null")
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
    public User(String firstName,String lastName,String email, String password) {
        this(firstName,lastName,email, password, UserRole.USER);
    }
    public User(String firstName, String lastName, String email,String password,UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.password=password;
        this.role=role;
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +'\''+
                '}';
    }

}
