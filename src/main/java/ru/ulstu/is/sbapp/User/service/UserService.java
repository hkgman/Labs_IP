package ru.ulstu.is.sbapp.User.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.repository.PostRepository;
import ru.ulstu.is.sbapp.User.model.User;

import ru.ulstu.is.sbapp.User.repository.UserRepository;
import ru.ulstu.is.sbapp.Util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentService commentService;
    private final ValidatorUtil validatorUtil;

    public UserService(UserRepository userRepository, ValidatorUtil validatorUtil, PostRepository postRepository, CommentService commentService)
    {
        this.userRepository=userRepository;
        this.validatorUtil=validatorUtil;
        this.postRepository = postRepository;
        this.commentService = commentService;
    }
    @Transactional
    public User addUser(String firstName, String lastName, String email) {
        final User user = new User(firstName, lastName, email);
        validatorUtil.validate(user);
        return userRepository.save(user);
    }

    @Transactional
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
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void addNewPost(Long id, PostDto postDto)
    {
        Optional<User> currentUser = userRepository.findById(id);
        if(currentUser.isPresent())
        {
            Post post = new Post(postDto);
            post.setUser(currentUser.get());
            postRepository.save(post);
        }
    }
    @Transactional
    public void deleteAllUsers() {
        commentService.deleteAllComments();
        userRepository.deleteAll();
    }


    @Transactional
    public List<Post> GetUserPosts(Long id)
    {
        return userRepository.getUsersPosts(id);
    }


    @Transactional
    public void deletePost(Long id, Long postId) {
        postRepository.deleteById(postId);
    }
}
