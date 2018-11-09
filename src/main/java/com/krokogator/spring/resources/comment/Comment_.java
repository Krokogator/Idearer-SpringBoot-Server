package com.krokogator.spring.resources.comment;

import com.krokogator.spring.resources.user.User;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.List;

@StaticMetamodel(Comment.class)
public class Comment_ {
    public static volatile SingularAttribute<Comment, Long> id;
    public static volatile ListAttribute<Comment, List<User>> likes;
}
