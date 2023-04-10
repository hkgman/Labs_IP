package ru.ulstu.is.sbapp.Post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.repository.CommentRepository;
import ru.ulstu.is.sbapp.Post.controller.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.repository.PostRepository;
import ru.ulstu.is.sbapp.User.repository.UserRepository;
import ru.ulstu.is.sbapp.Util.validation.ValidatorUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;

    public PostService(PostRepository postRepository,CommentRepository commentRepository,ValidatorUtil validatorUtil,UserRepository userRepository)
    {
        this.postRepository=postRepository;
        this.commentRepository=commentRepository;
        this.validatorUtil=validatorUtil;
        this.userRepository=userRepository;
    }
    @Transactional
    public Post addPost(PostDto postDto) {
        final Post post = new Post(postDto);
        validatorUtil.validate(post);
        return postRepository.save(post);
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
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Transactional
    public void addCommentToPost(Long id, Long userId, String text){
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Comment comment = new Comment(text);
            comment.setPost(optionalPost.get(), userRepository.findById(userId).get());
            commentRepository.save(comment);
        }
        postRepository.save(optionalPost.get());
    }

    @Transactional
    public void removeCommentFromPost(Long id, Long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        optionalComment.get().setPost(null, null);
        Optional <Post> postOptional = postRepository.findById(id);
        postOptional.get().getComments().remove(optionalComment.get());
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public List<Post> getPostsAndComments(String text)
    {
        return postRepository.getPostsAndComments(text);
    }
}
