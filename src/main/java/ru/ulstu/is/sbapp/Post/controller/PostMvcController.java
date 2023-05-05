package ru.ulstu.is.sbapp.Post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.model.CommentDto;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.Post.service.PostService;
import ru.ulstu.is.sbapp.User.model.UserDto;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Controller
@RequestMapping("/index")
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
        return "index";
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
        return "index";
    }
    @GetMapping(value = {"/edit/{id}"})
    public String editPost(@PathVariable(required = false) Long id,
                           Model model) {
            model.addAttribute("postId", id);
            model.addAttribute("postDto", new PostDto(postService.findPost(id)));
            return "post-edit";
    }

    @PostMapping(value = {"/{id}"})
    public String savePost(@PathVariable(required = false) Long id,
                           @RequestParam(value = "multipartFile") MultipartFile multipartFile,
                           @ModelAttribute @Valid PostDto postDto,
                           BindingResult bindingResult,
                           Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "post-edit";
        }
            postDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
            postService.updatePost(id, postDto);
            return "redirect:/user";
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(
                             @PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/user";
    }
    @PostMapping("/deleteComment/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long postId,
                             @PathVariable Long commentId,
                                Principal principal,Model model) {
        Comment comment = commentService.findComment(commentId);
        if(!comment.getUser().getLogin().equals(principal.getName())){
            model.addAttribute("error", new Exception("Вы не можете удалить не ваш комментарий"));
            return "error";
        }
        postService.removeCommentFromPost(postId,commentId);
        return "redirect:/index/{postId}";
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
        return "index";
    }


    @GetMapping("/addComment/{postId}")
    public String showCreateCommentInfo(@PathVariable(value = "postId") Long postId, Model model) {
        model.addAttribute("postId", postId);
        model.addAttribute("commentDto", new CommentDto());
        return "comment-create.html";
    }
    @PostMapping("/createComment/{postId}")
    public String createPost(@PathVariable(value = "postId") Long postId,
                             @ModelAttribute @Valid CommentDto commentDto,
                             BindingResult bindingResult,
                             Principal principal,
                             Model model) throws IOException
    {
        Long userId = userService.findByLogin(principal.getName()).getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "post-create";
        }
        postService.addCommentToPost(postId,userId,commentDto.getText());
        return "redirect:/index";
    }

    @GetMapping("/editComment/{Id}")
    public String showEditCommentInfo(@PathVariable(value = "Id") Long Id, Model model,Principal principal) throws Exception {
        model.addAttribute("Id", Id);
        CommentDto commentDto = new CommentDto(commentService.findComment(Id));
        model.addAttribute("commentDto",commentDto);
        if(!commentDto.getUser().equals(principal.getName())){
            model.addAttribute("error", new Exception("Вы не можете изменить не ваш комментарий"));
            return "error";
        }
        else
        {
            return "comment-edit";
        }

    }

    @PostMapping("/updateComment/{Id}")
    public String updateComment(@PathVariable(value = "Id") Long Id,
                             @ModelAttribute @Valid CommentDto commentDto,
                             BindingResult bindingResult,
                             Principal principal,
                             Model model) throws IOException
    {
        Long userId = userService.findByLogin(principal.getName()).getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "comment-edit";
        }
        commentService.updateComment(Id,commentDto.getText());
        return "redirect:/user";
    }
}
