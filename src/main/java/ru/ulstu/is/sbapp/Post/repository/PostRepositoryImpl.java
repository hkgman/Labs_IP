package ru.ulstu.is.sbapp.Post.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.repository.CommentRepository;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepositoryExtension{
    @Autowired
    @Lazy
    private PostRepository postRepository;

    @Autowired
    @Lazy
    private CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Post> safeRemove(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent())
        {
            em.createQuery("Delete from Comment where post.id = :id")
                    .setParameter("id",id)
                    .executeUpdate();
            em.remove(optionalPost.get());
        }
        return optionalPost;
    }

    @Override
    public void safeRemoveAll() {
        em.createQuery("delete from Comment").executeUpdate();
        em.createQuery("delete from Post").executeUpdate();
    }

    @Override
    public void addComment(Long id, Long userId, String text) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Comment comment = new Comment(text);
            comment.setPost(optionalPost.get(), em.find(User.class, userId));
            commentRepository.save(comment);
        }
    }

    @Override
    public void removeComment(Long id, Long commentId) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalPost.isPresent() & optionalComment.isPresent()) {
            optionalPost.get().getComments().remove(optionalComment.get());
            em.merge(optionalPost.get());
            optionalComment.get().setPost(null, null);
            em.createQuery("Delete from Comment where Id = :commentId")
                    .setParameter("commentId", commentId)
                    .executeUpdate();
        }
    }

}
