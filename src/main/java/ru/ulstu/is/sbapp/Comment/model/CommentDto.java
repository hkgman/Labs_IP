package ru.ulstu.is.sbapp.Comment.model;


public class CommentDto {
    private Long id;
    private String Text;
    private String userName;
    public CommentDto(){}
    public CommentDto(Comment comment)
    {
        this.id= comment.getId();
        this.Text=comment.getText();
        this.userName=comment.getUser().getFirstName() + " " + comment.getUser().getLastName();
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


}
