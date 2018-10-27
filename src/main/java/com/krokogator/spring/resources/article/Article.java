package com.krokogator.spring.resources.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krokogator.spring.resources.category.Category;
import com.krokogator.spring.resources.comment.Comment;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ApiModelProperty(readOnly = true)
    private Date created;

    @JsonIgnore
    @ManyToMany
    private List<User> likes;

    @ApiModelProperty(readOnly = true)
    @Transient
    private int likesCount;

    @ApiModelProperty(readOnly = true)
    @Transient
    private boolean liked;

    @JsonIgnore
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ApiModelProperty(readOnly = true)
    @Transient
    private int commentsCount;

    @PostLoad
    private void Load(){
        likesCount = likes.size();
        liked = likes.stream().anyMatch(x -> x.getId().equals(CurrentUser.getId()));
        commentsCount = comments.size();
    }

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    public Article(){
        this.likesCount = 0;
        this.liked = false;
        this.created = new Date();
    }

    public Article(String title, String content, User user, Category category){
        this();
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
    }

    public Article(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public List<User> getLikes(){
        return this.likes;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
