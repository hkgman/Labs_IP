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
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.service.PostService;
import ru.ulstu.is.sbapp.User.model.User;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.util.List;

@SpringBootTest
public class JpaPostTests {
    private static final Logger log = LoggerFactory.getLogger(JpaPostTests.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Test
    void TestAddPost(){
        postService.deleteAllPosts();
        final Post post = postService.addPost("Test","Test");
        Assertions.assertNotNull(post.getId());
    }

    @Test
    void TestFindPost(){
        postService.deleteAllPosts();
        final Post post = postService.addPost("Test","Test");
        final Post findPost = postService.findPost(post.getId());
        Assertions.assertEquals(post, findPost);
    }

    @Test
    void TestPostReadNotFound(){
        postService.deleteAllPosts();
        Assertions.assertThrows(EntityNotFoundException.class, () -> postService.findPost(-1L));
    }

    @Test
    void TestFindAllPost(){
        postService.deleteAllPosts();
        final Post firstPost = postService.addPost("Test1","Test1");
        final Post secondPost = postService.addPost("Test2","Test2");
        final List<Post> posts = postService.findAllPosts();
        Assertions.assertEquals(posts.size(), 2);
    }
    @Test
    void TestPostReadAllEmpty() {
        postService.deleteAllPosts();
        final List<Post> posts = postService.findAllPosts();
        Assertions.assertEquals(posts.size(), 0);
    }

    @Test
    void TestPostUpdate(){
        postService.deleteAllPosts();
        Post post = postService.addPost("Test1", "Test1");
        post = postService.updatePost(post.getId(), "Test2", "Test2");
        Assertions.assertEquals(post.getHeading(), "Test2");
        Assertions.assertEquals(post.getContent(), "Test2");
    }

    @Test
    void TestDeletePost(){
        postService.deleteAllPosts();
        final Post post = postService.addPost("Test","Test");
        postService.deletePost(post.getId());
        Assertions.assertThrows(EntityNotFoundException.class, () -> postService.findPost(1L));
    }

    @Test
    void TestAddComment()
    {
        postService.deleteAllPosts();
        userService.deleteAllUsers();
        final User user = userService.addUser("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        final Post post =userService.addNewPost(user.getId(),"Text1","Text2");
        final Comment comment =postService.addCommentToPost(post.getId(),user,"СОООСИИ УЕБОК ЕБАННЫЙ");
        final List<Comment> comments = commentService.findAllComments();
        log.info(comments.toString());
        final User user1=userService.findUser(user.getId());
        log.info(user1.getComments().toString());
        final  Post post1=postService.findPost(post.getId());
        log.info(post1.getComments().toString());
    }
}
