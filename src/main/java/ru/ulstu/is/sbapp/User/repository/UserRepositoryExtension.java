package ru.ulstu.is.sbapp.User.repository;

import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.Optional;

public interface UserRepositoryExtension {
    Optional<User> safeRemove(Long id);
    void safeRemoveAll();
    void addPost(Long id, String Heading, String Content);
    void removePost(Long id, Long postId);
}
