package ru.ulstu.is.sbapp.Post.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(PostService.class);
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
        em.createQuery("Delete from Comment where post.id = "+id).executeUpdate();
        em.remove(currentPost);
        return currentPost;
    }

    @Transactional
    public void deleteAllPosts() {
        em.createQuery("delete from Post").executeUpdate();
    }

    @Transactional
    public Comment addCommentToPost(Long id, User user, String text){
        final Post post = findPost(id);
        if(post == null){
            throw new IllegalArgumentException("Post with id " + id + " not found");
        }
        Comment comment=new Comment(text);
        comment.setPost(post, em.find(User.class, user.getId()));
        return em.merge(comment);
    }

    @Transactional
    public void removeCommentFromPost(Long id, Comment comment){
        final Post post = findPost(id);
        log.info(post.toString());
        post.getComments().remove(comment);
        em.merge(post);
        comment.setPost(null, null);
        em.createQuery("Delete from Comment where Id = "+comment.getId()).executeUpdate();
    }
}
