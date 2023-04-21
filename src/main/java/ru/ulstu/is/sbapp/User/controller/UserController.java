package ru.ulstu.is.sbapp.User.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.User.model.UserDto;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return new UserDto(userService.findUser(id));
    }
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAllUsers().stream()
                .map(UserDto::new)
                .toList();
    }
    @GetMapping("/{id}/posts")
    public List<PostDto> getPosts(@PathVariable Long id) {
        return userService.GetUserPosts(id).stream()
                .map(PostDto::new)
                .toList();
    }
    @PostMapping
    public UserDto createUser(@RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastname,
                                  @RequestParam("email") String email) {
        return new UserDto(userService.addUser(firstName, lastname,email));
    }

    @PutMapping("/{id}")
    public UserDto updateClient(@PathVariable Long id,
                                  @RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastname,
                                  @RequestParam("email") String email){
        return new UserDto(userService.updateUser(id, firstName, lastname,email));
    }
    @PostMapping("/{id}/Post")
    public void addPost(@PathVariable Long id,
                        @RequestBody @Valid PostDto postDto) {
        userService.addNewPost(id, postDto);
    }
    @DeleteMapping("/{id}/Post/{postId}")
    public void removePost(@PathVariable Long id,
                           @PathVariable Long postId)
    {
        userService.deletePost(id,postId);
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
