package ru.ulstu.is.sbapp.User.service;

import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.repository.PostRepository;
import ru.ulstu.is.sbapp.User.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ulstu.is.sbapp.User.model.UserDto;
import ru.ulstu.is.sbapp.User.model.UserRole;
import ru.ulstu.is.sbapp.User.model.UserSignupDto;
import ru.ulstu.is.sbapp.User.repository.UserRepository;
import ru.ulstu.is.sbapp.Util.validation.ValidatorUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentService commentService;

    private final PasswordEncoder passwordEncoder;
    private final ValidatorUtil validatorUtil;

    public UserService(UserRepository userRepository, ValidatorUtil validatorUtil, PostRepository postRepository, CommentService commentService,PasswordEncoder passwordEncoder)
    {
        this.userRepository=userRepository;
        this.validatorUtil=validatorUtil;
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.passwordEncoder=passwordEncoder;
    }
    public User findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }
    public Page<User> findAllPages(int page, int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").ascending()));
    }
    public User addUser(String firstName, String lastName, String email, String password) {
        return addUser(firstName,lastName, email, password, UserRole.USER);
    }
    @Transactional
    public User addUser(String login, String email, String password,String passwordConfirm, UserRole role) {
        if (findByLogin(login) != null) {
            throw new ValidationException(String.format("User '%s' already exists", login));
        }
        if (!Objects.equals(password, passwordConfirm)) {
            throw new ValidationException("Passwords not equals");
        }
        final User user = new User(login,email,passwordEncoder.encode(password),role);
        validatorUtil.validate(user);
        return userRepository.save(user);
    }
    @Transactional
    public User addUser(UserSignupDto userSignupDto) {
        if (findByLogin(userSignupDto.getLogin()) != null) {
            throw new ValidationException(String.format("User '%s' already exists", userSignupDto.getLogin()));
        }
        if (!Objects.equals(userSignupDto.getPassword(), userSignupDto.getPasswordConfirm())) {
            throw new ValidationException("Passwords not equals");
        }
        final User user = new User(userSignupDto);
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
    public User updateUser(Long id, String firstName, String email,String password) {
        final User currentUser = findUser(id);
        currentUser.setLogin(firstName);
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        validatorUtil.validate(currentUser);
        return userRepository.save(currentUser);
    }
    @Transactional
    public User updateUser(UserDto userDto) {
        final User currentUser = findUser(userDto.getId());
        final User sameUser = findByLogin(userDto.getLogin());
        if (sameUser != null && !Objects.equals(sameUser.getId(), currentUser.getId())) {
            throw new ValidationException(String.format("User '%s' already exists", userDto.getLogin()));
        }
        if (!passwordEncoder.matches(userDto.getPassword(), currentUser.getPassword())) {
            throw new ValidationException("Incorrect password");
        }
        currentUser.setLogin(userDto.getLogin());
        currentUser.setEmail(userDto.getEmail());
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userEntity = findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }
}
