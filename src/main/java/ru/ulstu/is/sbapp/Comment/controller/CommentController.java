package ru.ulstu.is.sbapp.Comment.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Configuration.OpenAPI30Configuration;

import java.util.List;
import java.util.Objects;

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

    @PutMapping("/{id}/curUser/{userId}/commentUser/{userComId}")
    public CommentDto updateComment(@PathVariable Long id,
                                    @PathVariable Long userId,
                                    @PathVariable Long userComId,
                                    @RequestParam("Text") String Text){
        if(Objects.equals(userComId, userId))
        {
            return new CommentDto(commentService.updateComment(id,Text));
        }
        return new CommentDto(commentService.findComment(id));
    }
    @DeleteMapping("/{id}")
    public CommentDto deleteComment(@PathVariable Long id) {
        return new CommentDto(commentService.deleteComment(id));
    }
}
