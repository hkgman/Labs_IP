package ru.ulstu.is.sbapp.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.User.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
