package ru.ulstu.is.sbapp.Post.repository;

import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryExtension {
    Optional<Post> safeRemove(Long id);
    void safeRemoveAll();
    void addComment(Long id,Long userId,String text);
    void removeComment(Long id, Long commentId);
    List<Comment> getPostComments(Long id);
}
