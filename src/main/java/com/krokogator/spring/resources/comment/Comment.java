package com.krokogator.spring.resources.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.user.LoggedInUser;
import com.krokogator.spring.resources.user.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {

    @ApiModelProperty(readOnly = true)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @ApiModelProperty(readOnly = true)
    private Date created;

    @JsonIgnore
    @OneToMany
    private List<User> likes;

    @ApiModelProperty(readOnly = true)
    @Transient
    private int likesCount;

    @ApiModelProperty(readOnly = true)
    @Transient
    private boolean liked;

    @PostLoad
    private void Load(){
        likesCount = likes.size();
        liked = likes.stream().anyMatch(x -> x.getId().equals(LoggedInUser.getId()));
    }

    public Comment(){
        this.created = new Date();
        this.liked = false;
        this.likesCount = 0;
    }

    public Comment(Long id) {
        this.id = id;
    }

    public Comment(String content, Article article, Comment parentComment, User user){
        this();
        this.content = content;
        this.article = article;
        this.parentComment = parentComment;
        this.user = user;
    }



    @ApiModelProperty(readOnly = true)
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Comment parentComment;

    @ApiModelProperty(readOnly = true)
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @JsonIgnore
    @ManyToOne
    private Article article;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
