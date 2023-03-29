package ru.ulstu.is.sbapp.User.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ru.ulstu.is.sbapp.Comment.service.CommentService;

import java.util.List;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    CommentService commentService;

    @Transactional
    public User addUser(String firstName, String lastName, String email) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName) ||!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Client info is null or empty");
        }
        final User user = new User(firstName, lastName,email);
        em.persist(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        final User user = em.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException(String.format("User with id [%s] is not found", id));
        }
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Transactional
    public User updateUser(Long id, String firstName, String lastName, String email) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName) ||!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("User info is null or empty");
        }
        final User currentUser = findUser(id);
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        return em.merge(currentUser);
    }

    @Transactional
    public User deleteUser(Long id) {
        final User currentUser = findUser(id);
        em.createQuery("Delete from Post Where user.id = "+ id).executeUpdate();
        em.createQuery("Delete from Comment Where user.id = "+id).executeUpdate();
        em.remove(currentUser);
        return currentUser;
    }

    @Transactional
    public void deleteAllUsers() {
        em.createQuery("Delete from Post").executeUpdate();
        em.createQuery("Delete from Comment").executeUpdate();
        em.createQuery("delete from User").executeUpdate();
    }
    @Transactional
    public Post addNewPost(Long id, String Heading,String Content) {
        User currentUser= findUser(id);
        Post post=new Post(Heading,Content);
        post.setUser(currentUser);
        return em.merge(post);
    }

    @Transactional
    public void deletePost(Long id, Post post) {
        em.createQuery("Delete Comment where post.Id = "+ post.getId()).executeUpdate();
        em.createQuery("Delete from Post where Id = "+post.getId()).executeUpdate();
    }


}
