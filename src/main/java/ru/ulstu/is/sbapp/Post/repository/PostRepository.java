package ru.ulstu.is.sbapp.Post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.Post.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
