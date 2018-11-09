package com.krokogator.spring.resources.comment;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentCustomRepository {

    List<Comment> getCommentsByAdvancedQuery(Long userId, Long articleId, Long parentCommentId, Pageable page, CommentSort sort);
}
