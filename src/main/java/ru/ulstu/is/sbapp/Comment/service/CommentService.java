package ru.ulstu.is.sbapp.Comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Comment addComment(Date DateOfAdd, String Text) {
        if(DateOfAdd==null)
        {
            throw new IllegalArgumentException("Date is null or empty");
        }
        if (!StringUtils.hasText(Text)) {
            throw new IllegalArgumentException("TEXT is null or empty");
        }
        final Comment comment = new Comment(DateOfAdd,Text);
        em.persist(comment);
        return comment;
    }

    @Transactional(readOnly = true)
    public Comment findComment(Long id) {
        final Comment comment = em.find(Comment.class, id);
        if (comment == null) {
            throw new EntityNotFoundException(String.format("Comment with id [%s] is not found", id));
        }
        return comment;
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }
    @Transactional
    public Comment updateComment(Long id, Date DateOfAdd,String Text) {
        if(DateOfAdd==null)
        {
            throw new IllegalArgumentException("Date is null or empty");
        }
        final Comment currentComment = findComment(id);
        currentComment.setDateOfAdd(DateOfAdd);
        currentComment.setText(Text);
        return em.merge(currentComment);
    }
    @Transactional
    public Comment deleteComment(Long id) {
        final Comment currentComment = findComment(id);
        em.remove(currentComment);
        return currentComment;
    }

    @Transactional
    public void deleteAllComments() {
        em.createQuery("delete from Comment").executeUpdate();
    }
    @Transactional
    public void addUser(Long id, User u) {
        final Comment comment = findComment(id);
        comment.setUser(u);
        em.merge(comment);
    }

    @Transactional
    public void deleteUser(Long id) {
        final Comment comment = findComment(id);
        comment.deleteUser();
        em.merge(comment);
    }
    @Transactional
    public void addPost(Long id, Post p) {
        final Comment comment = findComment(id);
        comment.setPost(p);
        em.merge(comment);
    }

    @Transactional
    public void deletePost(Long id) {
        final Comment comment = findComment(id);
        comment.deletePost();
        em.merge(comment);
    }

}