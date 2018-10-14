package com.krokogator.spring.resources.comment.projection;

import com.krokogator.spring.resources.user.User;

import java.util.Date;

public interface CommentWithoutChildrenProjection {
    long getId();

    String getContent();

    Date getCreated();

    int getLikesCount();

    boolean isLiked();

    User getUser();
}
