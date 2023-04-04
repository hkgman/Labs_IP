package ru.ulstu.is.sbapp.Comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.Comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
