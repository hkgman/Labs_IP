package ru.ulstu.is.sbapp.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select p from Post p where user.id = :id")
    List<Post> getUsersPosts(Long id);

    User findOneByLoginIgnoreCase(String login);

}
