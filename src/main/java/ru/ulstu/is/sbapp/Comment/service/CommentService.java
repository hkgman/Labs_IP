package ru.ulstu.is.sbapp.Comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ru.ulstu.is.sbapp.Comment.model.Comment;
import ru.ulstu.is.sbapp.Comment.repository.CommentRepository;
import ru.ulstu.is.sbapp.Util.validation.ValidatorUtil;


import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ValidatorUtil validatorUtil;

    public CommentService(CommentRepository commentRepository, ValidatorUtil validatorUtil)
    {
        this.commentRepository=commentRepository;
        this.validatorUtil=validatorUtil;
    }
    @Transactional
    public Comment addComment(String Text) {
        final Comment comment = new Comment(Text);
        validatorUtil.validate(comment);
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment findComment(Long id) {
        final Optional<Comment> client = commentRepository.findById(id);
        return client.orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
    @Transactional
    public Comment updateComment(Long id,String Text) {
        final Comment currentComment = findComment(id);
        currentComment.setText(Text);
        validatorUtil.validate(currentComment);
        return commentRepository.save(currentComment);
    }
    @Transactional
    public Comment deleteComment(Long id) {
        final Comment currentComment = findComment(id);
        commentRepository.delete(currentComment);
        return currentComment;
    }

    @Transactional
    public void deleteAllComments() {
        commentRepository.deleteAll();
    }



}