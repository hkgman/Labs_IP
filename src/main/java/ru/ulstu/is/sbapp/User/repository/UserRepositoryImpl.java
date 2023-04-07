package ru.ulstu.is.sbapp.User.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.repository.CommentRepository;
import ru.ulstu.is.sbapp.Post.controller.PostDto;
import ru.ulstu.is.sbapp.Post.model.Post;
import ru.ulstu.is.sbapp.Post.repository.PostRepository;
import ru.ulstu.is.sbapp.User.model.User;

import java.util.List;
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
            List<Post> posts = em.createQuery("Select p from Post p where user.id = :id",Post.class)
                    .setParameter("id",id)
                    .getResultList();
            for(var post : posts)
            {
                em.createQuery("Delete from Comment where post.id = :postId")
                        .setParameter("postId",post.getId())
                        .executeUpdate();
            }
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
    public void addPost(Long id, String Heading, String Content,byte[] image) {
        Optional<User> currentUser = userRepository.findById(id);
        if(currentUser.isPresent())
        {
            Post post = new Post(Heading, Content,image);
            post.setUser(currentUser.get());
            em.merge(post);
        }

    }

    @Override
    public void addPost(Long id, PostDto postdto) {
        Optional<User> currentUser = userRepository.findById(id);
        if(currentUser.isPresent())
        {
            Post post = new Post(postdto);
            post.setUser(currentUser.get());
            em.merge(post);
        }
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

    @Override
    public List<Post> getUsersPosts(Long id) {
        TypedQuery<Post> query =
                em.createQuery("Select p from Post p where user.id = :id",Post.class)
                        .setParameter("id",id);
        return query.getResultList();
    }
}
