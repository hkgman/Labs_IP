package ru.ulstu.is.sbapp;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.Comment.controller.CommentDto;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.service.CommentNotFoundException;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.User.service.UserService;


import java.util.Date;
import java.util.List;

@SpringBootTest
public class JpaCommentTest {
    private static final Logger log = LoggerFactory.getLogger(JpaCommentTest.class);
    @Autowired
    private CommentService commentService;

    @Test
    void testTidingCreate()
    {
        commentService.deleteAllComments();
        final Comment comment=commentService.addComment("Net");
        log.info("testCommentCreate: " + comment.toString());
        Assertions.assertNotNull(comment.getId());
        commentService.deleteAllComments();
    }
    @Test
    void testTidingRead()
    {
        commentService.deleteAllComments();
        final Comment comment=commentService.addComment("Net");
        log.info("testCommentRead[0]: " + comment.toString());
        final Comment findComment=commentService.findComment(comment.getId());
        log.info("testCommentRead[1]: " + findComment.toString());
        Assertions.assertEquals(comment, findComment);

        commentService.deleteAllComments();
    }
    @Test
    void testTidingReadNotFound() {
        commentService.deleteAllComments();
        Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.findComment(-1L));
    }
    @Test
    void testAllTidingRead()
    {
        commentService.deleteAllComments();
        commentService.addComment("Net");
        commentService.addComment("yep");
        final List<Comment> comments = commentService.findAllComments();
        log.info("testAllCommentRead: " + comments.toString());
        Assertions.assertEquals(comments.size(), 2);

        commentService.deleteAllComments();
    }
    @Test
    void testTidingReadAllEmpty() {
        commentService.deleteAllComments();
        final List<Comment> comments = commentService.findAllComments();
        log.info("testCommentReadAllEmpty: " + comments.toString());
        Assertions.assertEquals(comments.size(), 0);
    }
    @Test
    void testUpdateTiding()
    {
        commentService.deleteAllComments();
        final Comment comment=commentService.addComment("Net");
        log.info("testUpdateComment: " + comment.toString());
        commentService.updateComment(comment.getId(),"Ladno");
        final  Comment comment1=commentService.findComment(comment.getId());
        log.info("testUpdateComment: " + comment1.toString());
        Assertions.assertEquals(comment1.getText(), "Ladno");
        commentService.deleteAllComments();
    }

}
