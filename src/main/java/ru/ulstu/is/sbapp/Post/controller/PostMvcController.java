package ru.ulstu.is.sbapp.Post.controller;

import jakarta.servlet.ServletRegistration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ulstu.is.sbapp.Comment.model.CommentDto;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.Post.service.PostService;
import ru.ulstu.is.sbapp.User.model.UserDto;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

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
    @GetMapping("/filter")
    public String getFileteredPosts(@RequestParam(value = "searchValue") String searchValue,Model model)
    {
        model.addAttribute("posts",
                postService.getPostsAndComments(searchValue).stream()
                        .map(PostDto::new)
                        .toList());
        model.addAttribute("users",
                userService.findAllUsers().stream()
                        .map(UserDto::new)
                        .toList());
        return "post";
    }
    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editPost(@PathVariable(required = false) Long id,
                           Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("postDto", new PostDto());
            model.addAttribute("users",
                    userService.findAllUsers().stream()
                            .map(UserDto::new)
                            .toList());
            return "post-create";
        } else {
            model.addAttribute("postId", id);
            model.addAttribute("postDto", new PostDto(postService.findPost(id)));
            return "post-edit";
        }

    }

    @PostMapping(value = {"/user/", "/{id}"})
    public String savePost(@PathVariable(required = false) Long id,
                           @RequestParam(value = "userId",required = false) Long userId,
                           @RequestParam(value = "multipartFile") MultipartFile multipartFile,
                           @ModelAttribute @Valid PostDto postDto,
                           BindingResult bindingResult,
                           Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "post-edit";
        }
        if (id == null || id <= 0 && userId!=null) {
            postDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
            userService.addNewPost(userId,postDto);
        } else {
            postDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
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

    @GetMapping("/userPosts")
    public String getUserPosts(@RequestParam(value = "userId") Long userId,Model model)
    {
        model.addAttribute("posts",
                userService.GetUserPosts(userId).stream()
                        .map(PostDto::new)
                        .toList());
        model.addAttribute("users",
                userService.findAllUsers().stream()
                        .map(UserDto::new)
                        .toList());
        return "post";
    }


    @GetMapping(value = {"/addComment/{postId}", "/editComment/{id}"})
    public String editComment(@PathVariable(required = false) Long id,
                              @PathVariable(required = false) Long postId,
                           Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("postId", postId);
            model.addAttribute("users",
                    userService.findAllUsers().stream()
                            .map(UserDto::new)
                            .toList());
            model.addAttribute("commentDto", new CommentDto());
            return "comment-create.html";
        } else {
            model.addAttribute("id", id);
            model.addAttribute("commentDto", new CommentDto(commentService.findComment(id)));
            return "comment-edit";
        }

    }
    @PostMapping(value = {"/comment/{postId}/user", "/comment/{id}"})
    public String saveComment(@PathVariable(required = false) Long id,
                           @RequestParam(value = "userId",required = false) Long userId,
                          @PathVariable(required = false) Long postId,
                           @ModelAttribute @Valid CommentDto commentDto,
                           BindingResult bindingResult,
                           Model model,
                            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "comment-edit";
        }
        if (id == null || id <= 0 && userId!=null) {
            postService.addCommentToPost(postId,userId,commentDto.getText());
            return "redirect:/post/"+ postId;
        } else {
            commentService.updateComment(id, commentDto.getText());
            return "redirect:/post";
        }

    }
}
