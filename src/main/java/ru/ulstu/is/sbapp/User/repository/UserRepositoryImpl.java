package ru.ulstu.is.sbapp.User.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.ulstu.is.sbapp.Comment.repository.CommentRepository;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.repository.PostRepository;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepositoryExtension {

    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager em;
    @Override
    public Optional<User> safeRemove(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent())
        {
            em.createQuery("Delete from Comment Where user.id = :id")
                    .setParameter("id",id)
                    .executeUpdate();
            em.createQuery("Delete from Post Where user.id = :id")
                    .setParameter("id",id)
                    .executeUpdate();
            em.remove(optionalUser.get());
        }
        return optionalUser;
    }

    @Override
    public void safeRemoveAll() {
        em.createQuery("Delete from Comment").executeUpdate();
        em.createQuery("Delete from Post").executeUpdate();
        em.createQuery("delete from User").executeUpdate();
    }

    @Override
    public void addPost(Long id, String Heading, String Content) {
        Optional<User> currentUser = userRepository.findById(id);
        Post post = new Post(Heading, Content);
        post.setUser(currentUser.get());
        em.merge(post);
    }

    @Override
    public void removePost(Long id, Long postId) {
        em.createQuery("Delete Comment where post.Id = :postId")
                .setParameter("postId",postId)
                .executeUpdate();
        em.createQuery("Delete from Post where Id = :postId")
                .setParameter("postId",postId)
                .executeUpdate();
    }
}
