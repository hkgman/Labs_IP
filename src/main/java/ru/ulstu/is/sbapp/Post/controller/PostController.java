package ru.ulstu.is.sbapp.Post.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.Post.service.PostService;
import ru.ulstu.is.sbapp.User.controller.UserDto;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable Long id) {
        return new PostDto(postService.findPost(id));
    }
    @GetMapping
    public List<PostDto> getPosts() {
        return postService.findAllPosts().stream()
                .map(PostDto::new)
                .toList();
    }
    @PostMapping
    public PostDto createPost(@RequestBody @Valid PostDto postDto){
        return new PostDto(postService.addPost(postDto));
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable Long id,
                                @RequestParam("Heading") String Heading,
                                @RequestParam("Content") String Content){
        return new PostDto(postService.updatePost(id,Heading,Content));
    }
    @PostMapping("/{id}/Comment/{userId}")
    public void addComment(@PathVariable Long id,
                           @PathVariable Long userId,
                        @RequestParam("Text") String Text) {
        postService.addCommentToPost(id, userId,Text);
    }
    @DeleteMapping("/{id}/Comment/{postId}")
    public void removeComment(@PathVariable Long id,
                           @PathVariable Long commentId)
    {
        postService.removeCommentFromPost(id,commentId);
    }


    @DeleteMapping("/{id}")
    public PostDto deletePost(@PathVariable Long id) {
        return new PostDto(postService.deletePost(id));
    }
}
