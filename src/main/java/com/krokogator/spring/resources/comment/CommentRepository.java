package com.krokogator.spring.resources.comment;

import com.krokogator.spring.resources.comment.projection.CommentWithoutChildrenProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long>, CommentCustomRepository {
    Comment getById(Long id);

    List<Comment> getCommentsByArticleIdAndParentCommentId(Long articleId, Long parentCommentId);

    Page<Comment> findAll(Pageable pageable);

    List<CommentWithoutChildrenProjection> findAllByUserId(Long userId);

    List<CommentWithoutChildrenProjection> findAllByUserIdAndAndArticleId(Long userId, Long articleId);
}

