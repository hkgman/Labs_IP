package ru.ulstu.is.sbapp.Comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.Comment.model.Comment;


import java.util.List;

@Service
public class CommentService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Comment addComment(String Text) {
        if (!StringUtils.hasText(Text)) {
            throw new IllegalArgumentException("TEXT is null or empty");
        }
        final Comment comment = new Comment(Text);
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
    public Comment updateComment(Long id,String Text) {
        final Comment currentComment = findComment(id);
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



}