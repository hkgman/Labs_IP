package ru.ulstu.is.sbapp.Post.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.List;

@Service
public class PostService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Post addPost(String Heading, String Content) {
        if (!StringUtils.hasText(Heading) || !StringUtils.hasText(Content) ) {
            throw new IllegalArgumentException("Post info is null or empty");
        }
        final Post post = new Post(Heading,Content);
        em.persist(post);
        return post;
    }

    @Transactional(readOnly = true)
    public Post findPost(Long id) {
        final Post post = em.find(Post.class, id);
        if (post == null) {
            throw new EntityNotFoundException(String.format("Post with id [%s] is not found", id));
        }
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Transactional
    public Post updatePost(Long id, String Heading, String Content) {
        if (!StringUtils.hasText(Heading) || !StringUtils.hasText(Content) ) {
            throw new IllegalArgumentException("Post info is null or empty");
        }
        final Post currentPost = findPost(id);
        currentPost.setHeading(Heading);
        currentPost.setContent(Content);
        return em.merge(currentPost);
    }

    @Transactional
    public Post deletePost(Long id) {
        final Post currentPost = findPost(id);
        em.remove(currentPost);
        return currentPost;
    }

    @Transactional
    public void deleteAllTidings() {
        em.createQuery("delete from Post").executeUpdate();
    }
    @Transactional
    public void addUser(Long id, User u) {
        final Post post = findPost(id);
        post.setUser(u);
        em.merge(post);
    }

    @Transactional
    public void deleteUser(Long id) {
        final Post post = findPost(id);
        post.deleteUser();
        em.merge(post);
    }
    @Transactional
    public void addNewComment(Long id, Comment comment) {
        Post currentPost= findPost(id);
        currentPost.addNewComment(comment);
        em.merge(currentPost);
    }

    @Transactional
    public void deletePost(Long id, Comment comment) {
        Post currentPost= findPost(id);
        currentPost.deleteComment(comment);
        em.merge(currentPost);
    }
}
