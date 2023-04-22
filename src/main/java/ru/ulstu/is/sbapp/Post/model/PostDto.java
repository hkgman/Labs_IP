package ru.ulstu.is.sbapp.Post.model;

import ru.ulstu.is.sbapp.Comment.model.CommentDto;
import ru.ulstu.is.sbapp.Post.model.Post;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private Long id;

    private String heading;

    private String content;

    private Long userId;
    private List<CommentDto> comments = new ArrayList<>();
    private String image;


    public PostDto(){}
    public PostDto(Post post) {
        this.id = post.getId();
        this.heading = post.getHeading();
        this.content = post.getContent();
        this.image = new String(post.getImage(), StandardCharsets.UTF_8);
        if (post.getComments() != null) {
            comments = post.getComments().stream()
                    .map(CommentDto::new).toList();
        }
        userId=post.getUser().getId();
    }


    public Long getId()
    {
        return id;
    }
    public String getHeading()
    {
        return heading;
    }
    public String getContent()
    {
        return content;
    }

    public Long getUserId(){return userId;}

    public List<CommentDto> getComments()
    {
        return comments;
    }
    public String getImage() {
        return image;
    }

    public void setHeading(String Heading)
    {
        this.heading=Heading;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public void setUserId(Long id){this.userId=id;}

}
