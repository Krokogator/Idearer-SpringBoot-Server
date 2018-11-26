package com.krokogator.spring.resources.article;

import com.krokogator.spring.resources.user.User;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.List;

@StaticMetamodel(Article.class)
public class Article_ {
    public static volatile SingularAttribute<Article, Long> id;
    public static volatile SingularAttribute<Article, String> title;
    public static volatile ListAttribute<Article, List<User>> likes;
    public static volatile SingularAttribute<Article, ArticleStatus> status;
}
