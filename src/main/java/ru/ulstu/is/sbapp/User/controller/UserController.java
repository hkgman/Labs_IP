package ru.ulstu.is.sbapp.User.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.Configuration.OpenAPI30Configuration;
import ru.ulstu.is.sbapp.Post.controller.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;
import ru.ulstu.is.sbapp.User.model.UserRole;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.util.List;
import java.util.stream.IntStream;

@RestController
public class UserController {

    public static final String URL_LOGIN = "/jwt/login";
    public static final String URL_SIGN_UP = "/sign_up";
    public static final String URL_WHO_AM_I = "/who_am_i";
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return new UserDto(userService.findUser(id));
    }

    @PostMapping(URL_LOGIN)
    public String login(@RequestBody @Valid UserDto userDto) {
        return userService.loginAndGetToken(userDto);
    }
    @GetMapping(OpenAPI30Configuration.API_PREFIX + "/users")
    @Secured({UserRole.AsString.ADMIN})
    public Pair<Page<UserDto>, List<Integer>> getUsers(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "5") int size) {
        final Page<UserDto> users = userService.findAllPages(page, size)
                .map(UserDto::new);
        final int totalPages = users.getTotalPages();
        final List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .toList();
        return new Pair<>(users, pageNumbers);
    }
    @GetMapping(OpenAPI30Configuration.API_PREFIX + "/userList")
    public List<UserDto> getListUsers() {
        return userService.findAllUsers().stream()
                .map(UserDto::new)
                .toList();
    }
    @GetMapping(OpenAPI30Configuration.API_PREFIX +"/user/{id}/posts")
    public List<PostDto> getPosts(@PathVariable Long id) {
        return userService.GetUserPosts(id).stream()
                .map(PostDto::new)
                .toList();
    }
    @PostMapping(URL_SIGN_UP)
    public String signUp(@RequestBody @Valid UserSignupDto userSignupDto) {
        try {
            final User user = userService.addUser(userSignupDto.getLogin(), userSignupDto.getEmail(),
                    userSignupDto.getPassword(), userSignupDto.getPasswordConfirm(), UserRole.USER);
            return "created " + user.getLogin();
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }
    @GetMapping(OpenAPI30Configuration.API_PREFIX + "/user")
    public UserDto getUser(@RequestParam("login") String login) {
        User user = userService.findByLogin(login);
        return new UserDto(user);
    }

    @PostMapping(OpenAPI30Configuration.API_PREFIX + "/user")
    public String updateUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.updateUser(userDto);
            return "Profile updated";
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }
    @PostMapping("/user/{id}/Post")
    public void addPost(@PathVariable Long id,
                        @RequestBody @Valid PostDto postDto) {
        userService.addNewPost(id, postDto);
    }
    @DeleteMapping("/user/{id}/Post/{postId}")
    public void removePost(@PathVariable Long id,
                           @PathVariable Long postId)
    {
        userService.deletePost(id,postId);
    }


    @DeleteMapping(OpenAPI30Configuration.API_PREFIX + "/user/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public UserDto removeUser(@PathVariable Long id) {
        User user = userService.deleteUser(id);
        return new UserDto(user);
    }

    @GetMapping(URL_WHO_AM_I)
    public String whoAmI(@RequestParam("token") String token) {
        UserDetails userDetails = userService.loadUserByToken(token);
        User user = userService.findByLogin(userDetails.getUsername());
        return user.getRole().toString();
    }
}
