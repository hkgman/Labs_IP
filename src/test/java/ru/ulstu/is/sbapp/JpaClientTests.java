package ru.ulstu.is.sbapp;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.User.model.User;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.util.List;

@SpringBootTest
public class JpaClientTests {
    private static final Logger log = LoggerFactory.getLogger(JpaClientTests.class);
    @Autowired
    private UserService userService;

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
}
