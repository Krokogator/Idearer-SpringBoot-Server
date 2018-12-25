package com.krokogator.spring.resources.reject.article;

import com.krokogator.spring.resources.reject.RejectRepository;

import java.util.List;

public interface ArticleRejectRepository extends RejectRepository<ArticleReject> {
    List<ArticleReject> findAllByArticleId(Long articleId);
}
