package ru.ulstu.is.sbapp.User.repository;

import ru.ulstu.is.sbapp.Post.controller.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryExtension {
    Optional<User> safeRemove(Long id);
    void safeRemoveAll();
    void addPost(Long id, String Heading, String Content,byte[] image);
    void addPost(Long id, PostDto post);
    void removePost(Long id, Long postId);

    List<Post> getUsersPosts(Long id);
}
