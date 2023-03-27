package ru.ulstu.is.sbapp;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.User.model.User;
import ru.ulstu.is.sbapp.User.service.UserService;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class JpaTidingTest {
    private static final Logger log = LoggerFactory.getLogger(JpaTidingTest.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @Test
    void testTidingCreate()
    {
        commentService.deleteAllComments();
        final Date dateofadd= new Date(1212121212121L);
        final Comment comment=commentService.addComment(dateofadd,"Net");
        log.info("testCommentCreate: " + comment.toString());
        Assertions.assertNotNull(comment.getId());
        commentService.deleteAllComments();
    }
    @Test
    void testTidingRead()
    {
        commentService.deleteAllComments();
        final Date dateofadd= new Date(1212121212121L);
        final Comment comment=commentService.addComment(dateofadd,"Net");
        log.info("testCommentRead[0]: " + comment.toString());
        final Comment findComment=commentService.findComment(comment.getId());
        log.info("testCommentRead[1]: " + findComment.toString());
        Assertions.assertEquals(comment, findComment);

        commentService.deleteAllComments();
    }
    @Test
    void testTidingReadNotFound() {
        commentService.deleteAllComments();
        Assertions.assertThrows(EntityNotFoundException.class, () -> commentService.findComment(-1L));
    }
    @Test
    void testAllTidingRead()
    {
        commentService.deleteAllComments();
        final Date dateofadd1= new Date(112121212121L);
        final Date dateofadd2= new Date(1212121212121L);
        commentService.addComment(dateofadd1,"Net");
        commentService.addComment(dateofadd2,"yep");
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
        final Date dateofadd1= new Date(112121212121L);
        final Date dateofadd2= new Date(1212121212121L);
        final Comment comment=commentService.addComment(dateofadd1,"Net");
        log.info("testUpdateComment: " + comment.toString());
        commentService.updateComment(comment.getId(),dateofadd2,"Ladno");
        final  Comment comment1=commentService.findComment(comment.getId());
        log.info("testUpdateComment: " + comment1.toString());
        Assertions.assertEquals(comment1.getDateOfAdd(), dateofadd2);
        Assertions.assertEquals(comment1.getText(), "Ladno");
        commentService.deleteAllComments();
    }
    @Test
    void testSetAndDeleteUser()
    {
        commentService.deleteAllComments();
        userService.deleteAllUsers();
        final User user = userService.addUser("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        final Date dateofadd1= new Date("12/02/2020");
        final Comment comment=commentService.addComment(dateofadd1,"Net");
        commentService.addUser(comment.getId(),user);
        final User user1=userService.findUser(user.getId());
        final Comment comment1=commentService.findComment(comment.getId());
        Assertions.assertEquals(comment1.getUser(), user1);
        Assertions.assertEquals(user1.getComments().get(0), comment);
        commentService.deleteUser(user.getId());
        final User user2=userService.findUser(user.getId());
        final Comment comment2=commentService.findComment(comment.getId());
        Assertions.assertEquals(comment2.getUser(), null);
        Assertions.assertEquals(user2.getComments().size(), 0);
    }
}
