package ru.ulstu.is.sbapp.Post.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.Comment.model.CommentDto;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.Post.service.PostService;

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
    @GetMapping("/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Long id) {
        return postService.GetPostComments(id).stream()
                .map(CommentDto::new)
                .toList();
    }
    @PostMapping
    public PostDto createPost(@RequestBody @Valid PostDto postDto){
        return new PostDto(postService.addPost(postDto));
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable Long id,
                              @RequestBody @Valid PostDto postDto){
        return new PostDto(postService.updatePost(id,postDto));
    }
    @PostMapping("/{id}/Comment/{userId}")
    public void addComment(@PathVariable Long id,
                           @PathVariable Long userId,
                           @RequestParam("Text") String Text) {
        postService.addCommentToPost(id, userId,Text);
    }
    @DeleteMapping("/{id}/Comment/{commentId}")
    public void removeComment(@PathVariable Long id,
                              @PathVariable Long commentId)
    {
        postService.removeCommentFromPost(id,commentId);
    }


    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @DeleteMapping
    public void deleteall()
    {
        postService.deleteAllPosts();
    }

    @GetMapping("/filteredposts")
    public List<PostDto> getPostsAndComments(@RequestParam("Text") String Text){
        return postService.getPostsAndComments(Text).stream()
                .map(PostDto::new)
                .toList();
    }
}
