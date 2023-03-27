package ru.ulstu.is.sbapp;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.service.PostService;
import ru.ulstu.is.sbapp.User.model.User;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.util.List;

@SpringBootTest
public class JpaClientTests {
    private static final Logger log = LoggerFactory.getLogger(JpaClientTests.class);
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Test
    void testClientCreate() {
        userService.deleteAllUsers();
        final User user = userService.addUser("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        log.info("testUserCreate: " + user.toString());
        Assertions.assertNotNull(user.getId());

        userService.deleteAllUsers();
    }
    @Test
    void testReadClient()
    {
        userService.deleteAllUsers();
        final User user = userService.addUser("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        log.info("testUserRead[0]: " + user.toString());
        final  User curuser=userService.findUser(user.getId());
        log.info("testUserRead[1]: " + curuser.toString());
        Assertions.assertEquals(user, curuser);
        userService.deleteAllUsers();
    }
    @Test
    void testClientReadNotFound() {
        userService.deleteAllUsers();
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findUser(-1L));
    }
    @Test
    void testClientReadAll() {
        userService.deleteAllUsers();
        userService.addUser("Pupa","Lupa","sasdfdsf@gmail.com");
        userService.addUser("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        final List<User> users = userService.findAllUsers();
        log.info("testUserReadAll: " + users.toString());
        Assertions.assertEquals(users.size(), 2);

        userService.deleteAllUsers();
    }
    @Test
    void testClientReadAllEmpty() {
        userService.deleteAllUsers();
        final List<User> users = userService.findAllUsers();
        log.info("testUserReadAllEmpty: " + users.toString());
        Assertions.assertEquals(users.size(), 0);
    }

    @Test
    void testAddAndDeletePost()
    {
        postService.deleteAllPosts();
        userService.deleteAllUsers();
        final User user = userService.addUser("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        final Post post =userService.addNewPost(user.getId(),"Text1","Text2");
        final List<Post> posts =postService.findAllPosts();
        final User user1 = userService.findUser(user.getId());
        final List<Post> posts1 = user1.getPosts();
        log.info("testAddAndDeletePost :: ADD " + posts.toString() + " and " + posts1.toString());
        Assertions.assertEquals(posts.get(0).getUser(), user);
        Assertions.assertEquals(posts.toString(), posts1.toString());
        log.info("testAddAndDeletePost :: Delete ");
        userService.deletePost(user.getId(),post);
        final User us = userService.findUser(user.getId());
        Assertions.assertThrows(EntityNotFoundException.class, () -> postService.findPost(post.getId()));

        log.info(us.getPosts().toString());
        userService.deleteAllUsers();
        postService.deleteAllPosts();
    }
}
