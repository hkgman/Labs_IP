package ru.ulstu.is.sbapp.Comment.model;


public class CommentDto {
    private Long id;
    private String Text;
    private String userName;

    private Long postId;
    public CommentDto(){}
    public CommentDto(Comment comment)
    {
        this.id= comment.getId();
        this.Text=comment.getText();
        this.userName=comment.getUser().getLogin();
        this.postId=comment.getPost().getId();
    }
    public Long getId()
    {
        return id;
    }
    public String getText() {return  Text;}

    public String getUser()
    {
        return userName;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
