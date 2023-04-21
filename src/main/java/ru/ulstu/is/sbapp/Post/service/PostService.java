package ru.ulstu.is.sbapp.Post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.service.CommentService;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.repository.PostRepository;
import ru.ulstu.is.sbapp.User.service.UserService;
import ru.ulstu.is.sbapp.Util.validation.ValidatorUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final ValidatorUtil validatorUtil;

    public PostService(PostRepository postRepository, CommentService commentService, ValidatorUtil validatorUtil, UserService userService)
    {
        this.postRepository=postRepository;
        this.commentService = commentService;
        this.validatorUtil=validatorUtil;
        this.userService = userService;
    }
    @Transactional
    public Post addPost(PostDto postDto) {
        final Post post = new Post(postDto);
        validatorUtil.validate(post);
        return postRepository.save(post);
    }

    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post findPost(Long id) {
        final Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public Post updatePost(Long id, PostDto postDto) {
        final Post currentPost = findPost(id);
        currentPost.setHeading(postDto.getHeading());
        currentPost.setContent(postDto.getContent());
        currentPost.setImage(postDto.getImage().getBytes(StandardCharsets.UTF_8));
        validatorUtil.validate(currentPost);
        return postRepository.save(currentPost);
    }

    @Transactional
    public List<Comment> GetPostComments(Long id)
    {
        return postRepository.getPostComments(id);
    }
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllPosts() {
        commentService.deleteAllComments();
        postRepository.deleteAll();
    }

    @Transactional
    public void addCommentToPost(Long id, Long userId, String text){
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Comment comment = new Comment(text);
            comment.setPost(optionalPost.get(), userService.findUser(userId));
            commentService.saveComment(comment);
        }
        postRepository.save(optionalPost.get());
    }

    @Transactional
    public void removeCommentFromPost(Long id, Long commentId){
        Comment optionalComment = commentService.findComment(commentId);
        optionalComment.setPost(null, null);
        Optional <Post> postOptional = postRepository.findById(id);
        postOptional.get().getComments().remove(optionalComment);
        commentService.deleteComment(commentId);
    }

    @Transactional
    public List<Post> getPostsAndComments(String text)
    {
        return postRepository.getPostsAndComments(text);
    }
}
