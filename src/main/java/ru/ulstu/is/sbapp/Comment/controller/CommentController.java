package ru.ulstu.is.sbapp.Comment.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Configuration.OpenAPI30Configuration;

import java.util.List;

@RestController
@RequestMapping(OpenAPI30Configuration.API_PREFIX +  "/comment")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id) {
        return new CommentDto(commentService.findComment(id));
    }
    @GetMapping
    public List<CommentDto> getComments() {
        return commentService.findAllComments().stream()
                .map(CommentDto::new)
                .toList();
    }
    @PostMapping
    public CommentDto createComment(@RequestParam("Text") String Text){
        return new CommentDto(commentService.addComment(Text));
    }

    @PutMapping("/{id}")
    public CommentDto updateComment(@PathVariable Long id,
                                    @RequestParam("Text") String Text){
        return new CommentDto(commentService.updateComment(id,Text));
    }
    @DeleteMapping("/{id}")
    public CommentDto deleteComment(@PathVariable Long id) {
        return new CommentDto(commentService.deleteComment(id));
    }
}
