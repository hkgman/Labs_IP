package ru.ulstu.is.sbapp.Post.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.Comment.model.CommentDto;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.Post.service.PostService;
import ru.ulstu.is.sbapp.User.model.UserDto;
import ru.ulstu.is.sbapp.User.service.UserService;

@Controller
@RequestMapping("/post")
public class PostMvcController {
    private final PostService postService;
    private final  UserService userService;
    private final CommentService commentService;
    public PostMvcController(PostService postService,UserService userService,CommentService commentService)
    {
        this.postService = postService;
        this.userService = userService;
        this.commentService=commentService;
    }
    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id,Model model)
    {
        model.addAttribute("post",
                new PostDto(postService.findPost(id)));
        model.addAttribute("users",
                userService.findAllUsers().stream()
                        .map(UserDto::new)
                        .toList());
        model.addAttribute("comments",
                postService.GetPostComments(id).stream()
                        .map(CommentDto::new)
                        .toList());
        return "post-page";
    }
    @GetMapping
    public String getPosts(Model model) {
        model.addAttribute("posts",
                postService.findAllPosts().stream()
                        .map(PostDto::new)
                        .toList());
        model.addAttribute("users",
                userService.findAllUsers().stream()
                        .map(UserDto::new)
                        .toList());
        return "post";
    }

    @GetMapping(value = {"/add/{userId}", "/edit/{id}"})
    public String editPost(@PathVariable(required = false) Long id,
                           @PathVariable(required = false) Long userId,
                           Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("userId",userId);
            model.addAttribute("postDto", new PostDto());
            return "post-create";
        } else {
            model.addAttribute("postId", id);
            model.addAttribute("postDto", new PostDto(postService.findPost(id)));
            return "post-edit";
        }

    }

    @PostMapping(value = {"user/{userId}", "/{id}"})
    public String savePost(@PathVariable(required = false) Long id,
                           @PathVariable(required = false) Long userId,
                           @ModelAttribute @Valid PostDto postDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "post-edit";
        }
        if (id == null || id <= 0 && userId!=null) {
            userService.addNewPost(userId,postDto);
        } else {
            postService.updatePost(id, postDto);
        }
        return "redirect:/post";
    }

    @PostMapping("/delete/{id}/{postId}")
    public String deletePost(@PathVariable Long id,
                             @PathVariable Long postId) {
        userService.deletePost(id,postId);
        return "redirect:/post";
    }

    @PostMapping("/deleteComment/{id}/{commentId}")
    public String deleteComment(@PathVariable Long id,
                             @PathVariable Long commentId) {
        postService.removeCommentFromPost(id,commentId);
        return "redirect:/post";
    }
}
