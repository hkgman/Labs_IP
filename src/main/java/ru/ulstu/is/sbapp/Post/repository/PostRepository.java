package ru.ulstu.is.sbapp.Post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Post.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{
    @Query("Select c from Comment c where post.id = :id")
    List<Comment> getPostComments(Long id);

    @Query("Select p from Post p join p.comments c Where p.content Like concat('%', :text, '%') or c.Text Like concat('%', :text, '%')")
    List<Post> getPostsAndComments(String text);


}
