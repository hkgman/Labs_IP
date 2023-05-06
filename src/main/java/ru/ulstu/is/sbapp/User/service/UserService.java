package ru.ulstu.is.sbapp.User.service;

import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Configuration.Jwt.JwtException;
import ru.ulstu.is.sbapp.Configuration.Jwt.JwtProvider;
import ru.ulstu.is.sbapp.Configuration.PasswordEncoderConfiguration;
import ru.ulstu.is.sbapp.Post.controller.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.repository.PostRepository;
import ru.ulstu.is.sbapp.Post.service.PostService;
import ru.ulstu.is.sbapp.User.controller.UserDto;
import ru.ulstu.is.sbapp.User.model.User;

import ru.ulstu.is.sbapp.User.model.UserRole;
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
    private final ValidatorUtil validatorUtil;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, ValidatorUtil validatorUtil, PostRepository postRepository, CommentService commentService, PasswordEncoder passwordEncoder, JwtProvider jwtProvider)
    {
        this.userRepository=userRepository;
        this.validatorUtil=validatorUtil;
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }
    @Transactional
    public User addUser(String login, String email, String password, String passwordConfirm, UserRole role) {
        if (findByLogin(login) != null) {
            throw new ValidationException(String.format("User '%s' already exists", login));
        }
        if (!Objects.equals(password, passwordConfirm)) {
            throw new ValidationException("Passwords not equals");
        }
        final User user = new User(login, email, passwordEncoder.encode(password), role);
        validatorUtil.validate(user);
        return userRepository.save(user);
    }
    public User findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }

    public Page<User> findAllPages(int page, int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").ascending()));
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
    public User deleteUser(Long id) {
        final User currentUser = findUser(id);
        userRepository.delete(currentUser);
        return currentUser;
    }

    @Transactional
    public void deleteAllUsers() {
        commentService.deleteAllComments();
        userRepository.deleteAll();
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
    public List<Post> GetUserPosts(Long id)
    {
        return userRepository.getUsersPosts(id);
    }
    @Transactional
    public void deletePost(Long id, Long postId) {
        postRepository.deleteById(postId);
    }


    public String loginAndGetToken(UserDto userDto) {
        final User user = findByLogin(userDto.getLogin());
        if (user == null) {
            throw new UserNotFoundException(userDto.getLogin());
        }
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new ValidationException("Incorrect password");
        }
        return jwtProvider.generateToken(user.getLogin());
    }

    public UserDetails loadUserByToken(String token) throws UsernameNotFoundException {
        if (!jwtProvider.isTokenValid(token)) {
            throw new JwtException("Bad token");
        }
        final String userLogin = jwtProvider.getLoginFromToken(token)
                .orElseThrow(() -> new JwtException("Token is not contain Login"));
        return loadUserByUsername(userLogin);
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
