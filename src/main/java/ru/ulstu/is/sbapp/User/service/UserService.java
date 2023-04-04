package ru.ulstu.is.sbapp.User.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.service.PostNotFoundException;
import ru.ulstu.is.sbapp.User.model.User;

import ru.ulstu.is.sbapp.User.repository.UserRepository;
import ru.ulstu.is.sbapp.Util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;

    public UserService(UserRepository userRepository, ValidatorUtil validatorUtil)
    {
        this.userRepository=userRepository;
        this.validatorUtil=validatorUtil;
    }
    @Transactional
    public User addUser(String firstName, String lastName, String email) {
        final User user = new User(firstName, lastName, email);
        validatorUtil.validate(user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        final Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, String firstName, String lastName, String email) {
        final User currentUser = findUser(id);
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        validatorUtil.validate(currentUser);
        return userRepository.save(currentUser);
    }

    @Transactional
    public User deleteUser(Long id) {
        final Optional<User> user = userRepository.safeRemove(id);
        return user.orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.safeRemoveAll();
    }

    @Transactional
    public void addNewPost(Long id, String Heading, String Content) {
        userRepository.addPost(id,Heading,Content);
    }

    @Transactional
    public void deletePost(Long id, Long postId) {
        userRepository.removePost(id,postId);
    }
}
