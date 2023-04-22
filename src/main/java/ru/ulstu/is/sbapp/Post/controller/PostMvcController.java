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

    @PostMapping("/delete/{userId}/{postId}")
    public String deletePost(@PathVariable Long userId,
                             @PathVariable Long postId) {
        userService.deletePost(userId,postId);
        return "redirect:/post";
    }
    @PostMapping("/deleteComment/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long postId,
                             @PathVariable Long commentId) {
        postService.removeCommentFromPost(postId,commentId);
        return "redirect:/post/{postId}";
    }

    @GetMapping(value = {"/add/{postId}/{userId}", "/editComment/{id}"})
    public String editComment(@PathVariable(required = false) Long id,
                              @PathVariable(required = false) Long postId,
                           @PathVariable(required = false) Long userId,
                           Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("postId", postId);
            model.addAttribute("userId",userId);
            model.addAttribute("CommentDto", new CommentDto(commentService.findComment(id)));
            return "comment-create";
        } else {
            model.addAttribute("id", id);
            model.addAttribute("commentDto", new CommentDto(commentService.findComment(id)));
            return "comment-edit";
        }

    }

    @PostMapping(value = {"/{postId}/{userId}", "/comment/{id}"})
    public String saveComment(@PathVariable(required = false) Long id,
                           @PathVariable(required = false) Long userId,
                          @PathVariable(required = false) Long postId,
                           @ModelAttribute @Valid CommentDto commentDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "comment-edit";
        }
        if (id == null || id <= 0 && userId!=null) {
            postService.addCommentToPost(id,userId,commentDto.getText());
            return "redirect:/post-page/{postId}";
        } else {
            commentService.updateComment(id, commentDto.getText());
            return "redirect:/post";
        }

    }
}
